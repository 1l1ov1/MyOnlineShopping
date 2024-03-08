package com.wan.server;

import com.wan.dto.UserLoginDTO;
import com.wan.entity.User;

import java.util.Map;

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

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    User getUserById(Long userId);

    /**
     * 修改用户
     * @param user
     */
    User update(User user);

    /**
     * 根据用户id得到用户的详细信息
     * @param id
     * @return
     */
    User getDetail(Long id);

    /**
     * 用户重置密码
     * @param pwdData
     * @param id
     */
    void updatePassword(Map<String, String> pwdData, Long id);
}
