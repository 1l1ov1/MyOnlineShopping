package com.wan.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wan.constant.*;
import com.wan.dto.ForbiddenOrBanDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.StoreSales;
import com.wan.entity.User;
import com.wan.enumeration.StoreSalesRangeType;
import com.wan.exception.AccountExistException;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.ForbiddenOrBanException;
import com.wan.exception.StatusException;
import com.wan.mapper.AddressMapper;
import com.wan.mapper.ManagerMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.service.ManagerService;
import com.wan.vo.StoreSalesVO;
import com.wan.vo.StoreSalesWithStoreName;
import com.wan.vo.UserCountVO;
import com.wan.vo.UserPageQueryVO;
import com.wan.websocket.NoticeUserWebSocketServer;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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
                .banStartTime(LocalDateTime.now())
                .banEndTime(null)
                .id(id)
                .build();
        // 如果是用户封禁状态
        if (accountStatus == UserConstant.DISABLE) {
            // 通知用户退出
            NoticeUserLogout(user);
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
    public void forbidOrBan(ForbiddenOrBanDTO forbiddenOrBanDTO) {
        // 先得到类型
        String type = forbiddenOrBanDTO.getType();
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

                NoticeUserLogout(user);
            }
        } else {
            // 如果是其他的
            throw new ForbiddenOrBanException(MessageConstant.TYPE_IS_WORRY);
        }

        // 最后修改用户信息
        userMapper.update(user);
    }

    private void NoticeUserLogout(User user) {
        // 就让用户登出
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 并且通知用户
        Map<String, Object> map = new HashMap<>();
        map.put("type", WebSocketConstant.USER_EXIT);
        map.put("content", MessageConstant.ACCOUNT_IS_FOREVER_BAN);

        NoticeUserWebSocketServer.sendToSpecificUser(user.getId(), JSON.toJSONString(map));
    }
}


