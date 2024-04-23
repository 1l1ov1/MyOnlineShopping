package com.wan.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wan.constant.*;
import com.wan.dto.AwardUserDTO;
import com.wan.dto.ForbiddenOrBanDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.Report;
import com.wan.entity.User;
import com.wan.exception.*;
import com.wan.mapper.AddressMapper;
import com.wan.mapper.ManagerMapper;
import com.wan.mapper.ReportMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.service.ManagerService;
import com.wan.vo.UserPageQueryVO;
import com.wan.websocket.NoticeUserWebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ReportMapper reportMapper;

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
                .banStartTime(LocalDateTime.now())
                // 永久封禁
                .banEndTime(null)
                .id(id)
                .build();
        // 如果是用户封禁状态
        if (accountStatus == UserConstant.DISABLE) {
            // 通知用户退出
            NoticeUserMessage(user, WebSocketConstant.USER_EXIT, MessageConstant.ACCOUNT_IS_FOREVER_BAN);
        } else {
            // 如果是启用状态
            // 就将用户封禁时间清空
            user.setBanEndTime(null);
            user.setBanStartTime(null);
            // 然后通知用户，账号恢复
            NoticeUserMessage(user, WebSocketConstant.USER_START, MessageConstant.ACCOUNT_IS_UNBAN);
        }

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
        // 添加地址
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
        Address defaultAddress = addressMapper.getAddressByUserId(userPageQueryDTO.getId(), AddressConstant.IS_DEFAULT).get(0);
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
     * 禁言或封禁用户
     *
     * @param forbiddenOrBanDTO
     */
    @Override
    @Transactional
    public void forbidOrBan(ForbiddenOrBanDTO forbiddenOrBanDTO) {
        // 先得到类型
        String type = forbiddenOrBanDTO.getType();

        // 根据id查询举报
        Report report = reportMapper.findReportById(forbiddenOrBanDTO.getReportId());
        if (ObjectUtils.isEmpty(report)) {
            // 如果为空
            throw new ReportException(MessageConstant.REPORT_IS_NOT_EXIST);
        }


        // 得到被举报人的用户id
        Long reportedUserId = forbiddenOrBanDTO.getReportedUserId();
        User user = userMapper.getById(reportedUserId);
        // 得到被禁言的用户id
        if (ObjectUtils.isEmpty(user)) {
            // 如果用户为空
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果用户存在，得到他的账号状态
        Integer accountStatus = user.getAccountStatus();
        if (accountStatus == UserConstant.DISABLE) {
            // 如果账号本身处于封禁状态，就直接抛提示
            throw new ForbiddenOrBanException(MessageConstant.USER_HAS_BANNED + user.getBanEndTime());
        }


        if (ForbiddenOrBanConstant.FORBIDDEN.equals(type)) {
            // 如果是禁言
            // 如果是正常的就去禁言
            user.setForbiddenWord(UserConstant.FORBIDDEN_WORD);
            // 得到现在时间
            LocalDateTime now = LocalDateTime.now();
            // 得到结束时间（小时）
            Double forbiddenWordTime = forbiddenOrBanDTO.getForbiddenWordTime();
            user.setForbiddenStartTime(now);
            if (Objects.equals(forbiddenWordTime, ForbiddenOrBanConstant.FOREVER)) {
                // 如果是永久
                user.setForbiddenEndTime(null);
            } else {
                // 先加上整数部分给小时，在后面加上分钟
                Duration durationToAdd = Duration.ofHours(forbiddenWordTime.longValue())
                        .plusMinutes((long) (forbiddenWordTime % 1 * 60));
                user.setForbiddenEndTime(now.plus(durationToAdd));
            }

        } else if (ForbiddenOrBanConstant.BAN.equals(type)) {
            // 如果是封禁
            // 如果是正常的就去封禁
            user.setAccountStatus(UserConstant.DISABLE);
            LocalDateTime now = LocalDateTime.now();
            Double banTime = forbiddenOrBanDTO.getBanTime();
            user.setBanStartTime(now);
            // 如果是永久
            if (Objects.equals(banTime, ForbiddenOrBanConstant.FOREVER)) {
                user.setBanEndTime(null);
            } else {
                // 先加上整数部分给小时，在后面加上分钟
                Duration durationToAdd = Duration.ofHours(banTime.longValue())
                        .plusMinutes((long) (banTime % 1 * 60));
                user.setBanEndTime(now.plus(durationToAdd));
                // 通知用户退出
                NoticeUserMessage(user, WebSocketConstant.USER_EXIT, MessageConstant.USER_HAS_BANNED + user.getBanEndTime());
            }
        } else {
            // 如果是其他的
            throw new ForbiddenOrBanException(MessageConstant.TYPE_IS_WORRY);
        }

        // 最后修改用户信息
        userMapper.update(user);

        // 然后修改举报信息
        report.setStatus(ReportConstant.REPORT_STATUS_PROCESSED);
        reportMapper.updateReport(report);
    }

    /**
     * 管理员禁言或者解除禁言
     *
     * @param forbiddenOrBanDTO
     */
    @Override
    public void updateForbiddenWord(ForbiddenOrBanDTO forbiddenOrBanDTO) {
        String type = forbiddenOrBanDTO.getType();
        Long userId = forbiddenOrBanDTO.getReportedUserId();
        User user = userMapper.getById(userId);
        if (ObjectUtils.isEmpty(user)) {
            // 如果用户为空
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果是禁言
        if (ForbiddenOrBanConstant.FORBIDDEN.equals(type)) {
            // 如果用户不空
            // 得到结束范围
            Double forbiddenWordTime = forbiddenOrBanDTO.getForbiddenWordTime();
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.ofHours(forbiddenWordTime.longValue())
                    .plusMinutes((long) (forbiddenWordTime % 1 * 60));
            user.setForbiddenStartTime(now);
            user.setForbiddenEndTime(now.plus(duration));
            user.setForbiddenWord(UserConstant.FORBIDDEN_WORD);
        } else if (ForbiddenOrBanConstant.UN_FORBIDDEN.equals(type)) {
            // 如果是解除禁言
            user.setForbiddenStartTime(null);
            user.setForbiddenEndTime(null);
            user.setForbiddenWord(UserConstant.NOT_FORBIDDEN_WORD);
        }


        // 最后修改用户信息
        userMapper.update(user);
    }

    /**
     * 奖励用户
     *
     * @param awardUserDTO
     */
    @Override
    @Transactional
    public void awardUser(AwardUserDTO awardUserDTO) {
        Long userId = awardUserDTO.getUserId();
        Integer award = awardUserDTO.getAward();

        User user = userMapper.getById(userId);
        if (ObjectUtils.isEmpty(user)) {
            // 如果用户为空
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 如果用户不空，看金额是否合法
        if (award < UserConstant.MIN_REWARD_AMOUNT || award > UserConstant.MAX_REWARD_AMOUNT) {
            throw new AwardException(MessageConstant.AWARD_ACCOUNT_IS_VALID);
        }

        // 然后查看举报
        Report report = reportMapper.findReportById(awardUserDTO.getReportId());
        if (ObjectUtils.isEmpty(report)) {
            // 如果举报为空
            throw new ReportException(MessageConstant.REPORT_IS_NOT_EXIST);
        }

        // 如果举报不空
        // 奖励用户
        user.setMoney(user.getMoney().add(new BigDecimal(award)));
        userMapper.update(user);

        // 然后修改举报
        report.setIsAward(ReportConstant.REPORT_STATUS_REWARDED);
        reportMapper.updateReport(report);

        // 然后得到超级管理员，因为钱要从他那扣，他代表着平台
        User administrator = userMapper.getAdministrator(UserConstant.SUPER_ADMINISTRATOR);
        administrator.setMoney(administrator.getMoney().subtract(new BigDecimal(award)));
        // 然后修改
        userMapper.update(administrator);
        // 然后通知用户
        NoticeUserMessage(user, WebSocketConstant.USER_AWARD,
                MessageConstant.USER_REPORT_REWARD_SUCCESS + award + "元");

    }

    /**
     * 通知特定用户消息的方法。
     *
     * @param user    用户对象，表示需要被通知的用户。
     * @param type    消息类型，用于区分不同的消息。
     * @param content 消息内容，向用户展示的具体信息。
     */
    private void NoticeUserMessage(User user, Integer type, String content) {
        // 设置用户为不在线状态
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 准备消息数据并发送给用户
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("content", content);

        NoticeUserWebSocketServer.sendToSpecificUser(user.getId(), JSON.toJSONString(map));
    }
}


