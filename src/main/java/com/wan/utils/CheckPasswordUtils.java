package com.wan.utils;

import com.wan.constant.PasswordConstant;
import com.wan.exception.PasswordErrorException;

public class CheckPasswordUtils {

    private CheckPasswordUtils() {

    }

    public static Boolean checkPassword(String oldPwd, String newPwd, String rePwd) {
        // 去除字符串两端的空格，进行防御性编程
        oldPwd = oldPwd != null ? oldPwd.trim() : null;
        newPwd = newPwd != null ? newPwd.trim() : null;
        rePwd = rePwd != null ? rePwd.trim() : null;

        // 如果密码为空
        if (oldPwd == null || oldPwd.isEmpty() ||
                newPwd == null || newPwd.isEmpty() ||
                rePwd == null || rePwd.isEmpty()) {
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
        // 去除字符串两端的空格，进行防御性编程
        newPwd = newPwd != null ? newPwd.trim() : null;
        rePwd = rePwd != null ? rePwd.trim() : null;
        // 如果密码为空
        if (newPwd == null || newPwd.isEmpty() ||
                rePwd == null || rePwd.isEmpty()){
            throw new PasswordErrorException(PasswordConstant.PASSWORD_IS_NULL);
        }

        // 新密码和验证密码不同
        if (!newPwd.equals(rePwd)) {
            throw new PasswordErrorException(PasswordConstant.NEW_PASSWORD_IS_NOT_EQUALS_RE_PASSWORD);
        }

        return true;
    }
}
