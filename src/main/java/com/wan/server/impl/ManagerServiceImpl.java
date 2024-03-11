package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wan.constant.AddressConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.StoreConstant;
import com.wan.constant.UserConstant;
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
    @Autowired
    private StoreMapper storeMapper;

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

    /**
     * 分页查询商店
     *
     * @param storePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(StorePageQueryDTO storePageQueryDTO) {
        // 开启分页
        PageHelper.startPage(storePageQueryDTO.getPage(), storePageQueryDTO.getPageSize());

        Page<StorePageQueryVO> pages = managerMapper.storePageQuery(storePageQueryDTO);
        return PageResult.builder()
                .total(pages.getTotal())
                .data(pages.getResult())
                .build();

    }

    /**
     * 修改商店
     *
     * @param storePageQueryDTO
     */
    @Override
    public void updateStore(StorePageQueryDTO storePageQueryDTO) {
        Store store = new Store();
        BeanUtils.copyProperties(storePageQueryDTO, store);
        storeMapper.update(store);
    }

    /**
     * 得到商店详情
     *
     * @param id
     * @return
     */
    @Override
    public StorePageQueryVO getStoreDetail(Long id) {
        Store store = storeMapper.findStoreById(id);
        if (store == null) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }

        return managerMapper.getStoreDetail(id);
    }

    /**
     * 添加商店
     *
     * @param storePageQueryDTO
     */
    @Override
    public void addStore(StorePageQueryDTO storePageQueryDTO) {
        String storeName = storePageQueryDTO.getStoreName();
        String username = storePageQueryDTO.getUsername();
        // 先根据用户名查询该用户
        User user = userMapper.getByUsername(username);
        // 如果用户不存在就抛出异常
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果用户身份为管理员
        if (UserConstant.MANAGER == user.getStatus()) {
            throw new StoreException(MessageConstant.MANAGE_IS_NOT_ALLOWED_TO_OPEN_STORE);
        }
        // 如果用户身份为普通用户
        if (UserConstant.COMMON_USER == user.getStatus()) {
            throw new StoreException(MessageConstant.COMMON_USER_IS_NOT_ALLOWED_TO_OPEN_STORE);
        }
        // 根据店名查询商店
        Store store = storeMapper.findStoreByStoreName(storeName);
        // 如果店名已经存在
        if (store != null) {
            throw new StoreException(MessageConstant.STORE_EXIST);
        }
        // 根据userId查询商店
        store = storeMapper.findStoreByUserId(user.getId());
        // 如果用户已经有商店
        if (store != null) {
            throw new StoreException(MessageConstant.ONLY_HAS_ONE_STORE);
        }

        if ("".equals(storeName) || storeName == null) {
            throw new StoreException(MessageConstant.STORE_NAME_LENGTH_VALID);
        }
        int len = storeName.length();

        if (len < 2 || len > 15) {
            throw new StoreException(MessageConstant.STORE_NAME_LENGTH_VALID);
        }
        // 创建商店
        store = Store.builder()
                .storeName(storeName)
                .userId(user.getId())
                .status(StoreConstant.OPEN)
                .build();

        storeMapper.insertStore(store);

    }

    /**
     * 批量删除商店
     *
     * @param ids
     */
    @Override
    public void deleteBatchStore(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }
        storeMapper.deleteByIds(ids);
    }

    @Override
    public void openOrClose(Integer status, Long id) {
        // 如果状态不存在
        if (!status.equals(StoreConstant.OPEN) && !status.equals(StoreConstant.CLOSE)) {
            throw new StatusException(MessageConstant.THE_STATUS_IS_NOT_EXIST);
        }

        Store store = Store.builder()
                .status(status)
                .id(id)
                .build();
        storeMapper.update(store);

    }
}
