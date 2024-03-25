package com.wan.server.impl;

import com.wan.constant.*;
import com.wan.dto.UserCreateStoreDTO;
import com.wan.dto.UserLoginDTO;
import com.wan.entity.Address;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.exception.*;
import com.wan.mapper.AddressMapper;
import com.wan.mapper.StoreMapper;
import com.wan.mapper.UserMapper;
import com.wan.server.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private StoreMapper storeMapper;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User user = isValid(userLoginDTO);
        // 修改用户的登录状态
        user.setIsOnline(UserConstant.IS_ONLINE);
        userMapper.update(user);
        return user;
    }

    /**
     * 用户注册
     *
     * @param userLoginDTO
     */
    @Override
    public void addUser(UserLoginDTO userLoginDTO) {
        // 先按照用户名查询用户
        User user = userMapper.getByUsername(userLoginDTO.getUsername());
        // 如果该用户存在
        if (user != null) {
            throw new AccountExistException(MessageConstant.ACCOUNT_EXIST);
        }
        // 如果用户不存在
        user = new User();
        // 将DTO属性的值拷贝到user中
        BeanUtils.copyProperties(userLoginDTO, user);
        // 设置状态为启用
        user.setAccountStatus(UserConstant.ENABLE);
        // 设置用户为普通用户
        user.setStatus(UserConstant.COMMON_USER);
        // 设置用户的在线状态
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 将密码修改为MD5后保存
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        // 插入用户
        userMapper.insert(user);
    }

    /**
     * 根据id得到用户
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Long userId) {
        return userMapper.getById(userId);
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public User getDetail(Long id) {
        User user = userMapper.getById(id);
        return user;
    }

    @Override
    public void updatePassword(Map<String, String> pwdData, Long id) {
        String oldPwd = pwdData.get("oldPwd");
        String newPwd = pwdData.get("newPwd");
        String rePwd = pwdData.get("rePwd");

        // 如果密码为空
        if (oldPwd == null || newPwd == null || rePwd == null
                || "".equals(oldPwd) || "".equals(newPwd) || "".equals(rePwd)) {
            throw new PasswordErrorException(PasswordConstant.PASSWORD_IS_NULL);
        }

        // 如果新旧密码相同
        if (oldPwd.equals(newPwd)) {
            throw new PasswordErrorException(PasswordConstant.NEW_PASSWORD_EQUALS_OLD_PASSWORD);
        }
        // 新密码和验证密码不同
        if (!newPwd.equals(rePwd)) {
            throw new PasswordErrorException(PasswordConstant.NEW_PASSWORD_IS_NOT_EQUALS_RE_PASSWORD);
        }
        // 创建对象
        User user = User.builder()
                .id(id)
                .password(DigestUtils.md5DigestAsHex(newPwd.getBytes()))
                .build();
        // 更新密码
        userMapper.update(user);

    }

    @Override
    @Transactional
    public void createStore(UserCreateStoreDTO userCreateStoreDTO) {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username(userCreateStoreDTO.getUsername())
                .password(userCreateStoreDTO.getPassword()).build();

        User user = isValid(userLoginDTO);

        Store store = userCreateStoreDTO.getStore();
        // 如果没有这个名字的商店，就找这个用户是否有商店
        Store userStore = storeMapper.findStoreByUserId(user.getId());
        // 如果有
        if (userStore != null) {
            throw new StoreException(MessageConstant.ONLY_HAS_ONE_STORE);
        }
        // 得到商店
        Store storeByStoreName = storeMapper.findStoreByStoreName(store.getStoreName());
        if (storeByStoreName != null) {
            throw new StoreException(MessageConstant.STORE_EXIST);
        }
        // 如果没有
        store.setUserId(user.getId());
        store.setStatus(StoreConstant.OPEN);
        // 添加商店
        storeMapper.insertStore(store);
        // 得到地址
        Address address = userCreateStoreDTO.getAddress();
        if (address == null) {
            throw new StoreException(MessageConstant.STORE_ADDRESS_IS_NOT_ALLOWED_TO_BE_EMPTY);
        }
        // 成为店家
        user.setStatus(UserConstant.BUSINESSMAN);
        userMapper.update(user);
        // 添加地址
        address.setStoreId(store.getId());
        address.setIsDefault(AddressConstant.IS_DEFAULT);
        addressMapper.insertStoreAddress(address);
    }

    /**
     * 判断账号是否合法，如果合法就将用户返回
     *
     * @param userLoginDTO
     * @return
     */
    private User isValid(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        // 得到MD5加密后的密码
        String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        // 从数据库中按用户名查找
        User user = userMapper.getByUsername(username);
        // 如果账号不存在
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 如果账号存在，看看密码是否一致
        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 如果账号密码输对，看看是否被禁用
        // 如果被禁用
        if (user.getAccountStatus() == UserConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }


        return user;
    }
}
