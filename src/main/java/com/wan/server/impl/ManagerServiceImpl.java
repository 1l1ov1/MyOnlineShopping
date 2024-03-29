package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wan.constant.AddressConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.StoreConstant;
import com.wan.constant.UserConstant;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.dto.StorePageQueryDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.exception.AccountExistException;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.StatusException;
import com.wan.exception.StoreException;
import com.wan.mapper.AddressMapper;
import com.wan.mapper.ManagerMapper;
import com.wan.mapper.StoreMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.server.ManagerService;
import com.wan.vo.StorePageQueryVO;
import com.wan.vo.UserPageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        // 开启分页
        PageInfo<UserPageQueryVO> pageInfo = PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize())
                .doSelectPageInfo(() -> managerMapper.pageQuery(userPageQueryDTO));
        // 先查询用户
        // Page<UserPageQueryVO> pages = managerMapper.pageQuery(userPageQueryDTO);

        long total = pageInfo.getTotal();
        return PageResult.builder()
                .total(total)
                .data(pageInfo.getList())
                .build();
    }


    /**
     * 启用和禁用用户账号
     *
     * @param accountStatus
     * @param id
     */
    @Override
    public void startOrStop(Integer accountStatus, Long id) {
        // 如果状态不存在
        if (accountStatus != UserConstant.ENABLE && accountStatus != UserConstant.DISABLE) {
            throw new StatusException(MessageConstant.THE_STATUS_IS_NOT_EXIST);
        }
        User user = User.builder()
                .accountStatus(accountStatus)
                .id(id)
                .build();
        userMapper.update(user);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        userMapper.deleteByIds(ids);
    }

    /**
     * 管理员添加用户
     *
     * @param userPageQueryDTO
     */
    @Override
    @Transactional
    public void addUser(UserPageQueryDTO userPageQueryDTO) {
        // 先根据用户账号查询用户是否存在
        User query = userMapper.getByUsername(userPageQueryDTO.getUsername());
        if (query != null) {
            throw new AccountExistException(MessageConstant.ACCOUNT_EXIST);
        }
        // 如果账号不存在
        // 先将该用户的密码加密
        String md5 = DigestUtils.md5DigestAsHex(userPageQueryDTO.getPassword().getBytes());
        userPageQueryDTO.setPassword(md5);
        // 将账号状态设置为启用状态
        userPageQueryDTO.setAccountStatus(UserConstant.ENABLE);
        // 将账号设置为不在线
        userPageQueryDTO.setIsOnline(UserConstant.IS_NOT_ONLINE);
        User user = new User();
        BeanUtils.copyProperties(userPageQueryDTO, user);
        // 然后插入用户
        userMapper.insert(user);
        // 得到地址
        Address address = userPageQueryDTO.getAddressList().get(0);
        // 设置id
        address.setUserId(user.getId());
        address.setIsDefault(AddressConstant.IS_DEFAULT);
        //添加地址
        addressMapper.insertAddress(address);
    }

    /**
     * 修改用户
     *
     * @param userPageQueryDTO
     */
    @Override
    @Transactional // 开启事务
    public void updateUser(UserPageQueryDTO userPageQueryDTO) {
        // 先修改用户的信息
        managerMapper.update(userPageQueryDTO);
        // 从前端得到地址
        List<Address> addressList = userPageQueryDTO.getAddressList();
        // 先判断之前是否有默认地址，因为已经规定了如果有地址那么一定有一个默认地址
        Address defaultAddress = addressMapper.getAddressByUserId(userPageQueryDTO.getId(), AddressConstant.IS_DEFAULT);
        // 如果数据库中没有， 即没有地址
        if (defaultAddress == null) {
            // 如果没有就是添加
            // 改为默认
            addressList.get(0).setIsDefault(AddressConstant.IS_DEFAULT);
            // 插入
            addressMapper.insertAddress(addressList.get(0));
        } else {
            // 否则就是修改
            addressMapper.updateBatch(addressList);
        }

    }

}
