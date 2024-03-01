package com.wan.server;

import com.wan.dto.UserLoginDTO;
import com.wan.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param userLoginDTO
     */
    void addUser(UserLoginDTO userLoginDTO);
}
