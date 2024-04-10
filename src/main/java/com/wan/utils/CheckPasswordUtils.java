package com.wan.utils;

import com.wan.constant.PasswordConstant;
import com.wan.exception.PasswordErrorException;

public class CheckPasswordUtils {

    private CheckPasswordUtils() {

    }

    public static Boolean checkPassword(String oldPwd, String newPwd, String rePwd) {
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

        return true;
    }

    public static Boolean checkPassword(String newPwd, String rePwd) {
        // 如果密码为空
        if (newPwd == null || rePwd == null
                 || "".equals(newPwd) || "".equals(rePwd)) {
            throw new PasswordErrorException(PasswordConstant.PASSWORD_IS_NULL);
        }

        // 新密码和验证密码不同
        if (!newPwd.equals(rePwd)) {
            throw new PasswordErrorException(PasswordConstant.NEW_PASSWORD_IS_NOT_EQUALS_RE_PASSWORD);
        }

        return true;
    }
}
