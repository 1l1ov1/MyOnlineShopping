package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.AddressConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.UserConstant;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.User;
import com.wan.exception.AccountExistException;
import com.wan.mapper.AddressMapper;
import com.wan.mapper.ManagerMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.server.ManagerService;
import com.wan.vo.UserPageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<UserPageQueryVO> pages = managerMapper.pageQuery(userPageQueryDTO);
        long total = pages.getTotal();
        return PageResult.builder()
                .total(total)
                .data(pages.getResult())
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
        User user = User.builder()
                .accountStatus(accountStatus)
                .id(id)
                .build();
        userMapper.update(user);
    }

    @Override
    public void patchDelete(List<Long> ids) {
        userMapper.deleteByIds(ids);
    }

    /**
     * 管理员添加用户
     *
     * @param user
     */
    @Override
    public void addUser(User user) {
        // 先根据用户账号查询用户是否存在
        User query = userMapper.getByUsername(user.getUsername());
        if (query != null) {
            throw new AccountExistException(MessageConstant.ACCOUNT_EXIST);
        }
        // 如果账号不存在
        // 先将该用户的密码加密
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        // 将账号状态设置为启用状态
        user.setAccountStatus(UserConstant.ENABLE);
        // 将账号设置为不在线
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 然后插入用户
        userMapper.insert(user);
    }

    /**
     * 修改用户
     * @param userPageQueryDTO
     */
    @Override
    public void updateUser(UserPageQueryDTO userPageQueryDTO) {
        // 先修改用户的信息
        managerMapper.update(userPageQueryDTO);
        // 从前端得到默认地址
        Address address = userPageQueryDTO.getAddress();
        // 先判断之前是否有默认地址，因为已经规定了如果有地址那么一定有一个默认地址
        Address defaultAddress = addressMapper.getAddressByUserId(userPageQueryDTO.getId(), AddressConstant.IS_DEFAULT);
        // 如果数据库中没有
        if (defaultAddress == null ) {
            // 如果没有就是添加
            // 改为默认
            address.setIsDefault(AddressConstant.IS_DEFAULT);
            // 插入
            addressMapper.insertAddress(address);
        } else {
            // 否则就是修改
            addressMapper.updateDefaultAddress(address);
        }

    }
}
