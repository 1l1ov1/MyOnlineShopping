package com.wan.schedule;

import com.wan.constant.UserConstant;
import com.wan.entity.User;
import com.wan.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
/**
 * 用户定时器
 */
public class UserSchedule {

    @Autowired
    private UserMapper userMapper;
    // 每次处理100个用户
    private static final int batchSize = 100;

    /**
     * 定时处理用户账户状态，主要用于解封当天封禁结束的用户。
     * 每30分钟执行一次，从数据库中获取当天封禁结束的用户，将他们的封禁状态移除，恢复账户正常使用状态。
     */
    // @Scheduled(cron = "0 0/30 0 * * ?")
    @Scheduled(cron = "0/60 * * * * ?")
    public void processUserAccountStatus() {
        log.info("用户封禁定时器启动...");
        try {
            // 获取当前时间，用于查询当天需要被处理的用户
            LocalDateTime now = LocalDateTime.now();
            // 查询封禁结束时间在当前时间的用户列表
            List<User> userList = userMapper.findBanEndTimeToday(now);

            // 分批处理用户，以减小对数据库的操作压力
            // 定义每批次处理的用户数量
            for (int i = 0; i < userList.size(); i += batchSize) {
                // 计算每批次的起始和结束索引
                int toIndex = Math.min(i + batchSize, userList.size());
                // 获取当前批次的用户列表
                List<User> batchUserList = userList.subList(i, toIndex);

                // 遍历批次用户，设置账户状态为启用，移除封禁起止时间
                batchUserList.forEach(user -> {
                    user.setBanStartTime(null);
                    user.setBanEndTime(null);
                    user.setAccountStatus(UserConstant.ENABLE);
                });

                // 批量更新用户信息
                userMapper.batchUpdateUsers(batchUserList);
            }
        } catch (Exception e) {
            // 捕获异常，记录日志
            log.error("处理用户封禁状态时发生异常: {}", e.getMessage(), e);
        }

    }


    /**
     * 定时处理用户禁言的函数。
     * 该方法每30分钟执行一次，用于将禁言时间已过的用户的禁言状态恢复为正常。
     * 通过分批处理用户来减少对数据库的压力。
     */
    // @Scheduled(cron = "0 0/30 0 * * ?")
    @Scheduled(cron = "0/60 * * * * ?") // 每30分钟执行，用户处理用户禁言
    public void processUserForbiddenWord() {
        log.info("用户禁言定时器启动...");
        try {
            // 获取当前时间，用于与用户禁言结束时间比较
            LocalDateTime now = LocalDateTime.now();

            // 查询数据库中所有禁言结束时间在当前时间之前的用户
            List<User> userList = userMapper.findForbiddenEndTime(now);

            // 分批更新用户禁言状态，以减小单次数据库操作的压力
            for (int i = 0; i < userList.size(); i += batchSize) {
                // 计算每批处理的起始和结束索引
                int toIndex = Math.min(i + batchSize, userList.size());
                // 获取当前批次的用户列表
                List<User> batchUserList = userList.subList(i, toIndex);

                // 遍历当前批次用户，清除禁言开始和结束时间，设置为不禁言状态
                batchUserList.forEach(user -> {
                    user.setForbiddenStartTime(null);
                    user.setForbiddenEndTime(null);
                    user.setForbiddenWord(UserConstant.NOT_FORBIDDEN_WORD);
                });
                // 批量更新用户信息
                userMapper.batchUpdateUsers(batchUserList);
            }
        } catch (Exception e) {
            // 记录处理过程中可能出现的异常
            log.error("处理用户封禁状态时发生异常: {}", e.getMessage(), e);
        }
    }

}
