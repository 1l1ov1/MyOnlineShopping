package com.wan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wan.constant.AddressConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.UserConstant;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.StoreSales;
import com.wan.entity.User;
import com.wan.enumeration.StoreSalesRangeType;
import com.wan.exception.AccountExistException;
import com.wan.exception.AccountNotFountException;
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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
     * 查询用户人数
     *
     * @return
     */
    @Override
    public Integer userCount() {
        return userMapper.getUserCount();
    }

    /**
     * 查询一段时间的营业额
     *
     * @param type
     * @return
     */
    @Override
    public StoreSalesVO queryStoreSalesInOneDay(Integer type) {
        // 得到对应的枚举类型
        StoreSalesRangeType storeSalesRangeType = StoreSalesRangeType.getStoreSalesRangeType(type);
        if (storeSalesRangeType == null) {
            throw new IllegalArgumentException("查询营业额的类型无效");
        }
        Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();
        // 得到营业额
        List<StoreSalesWithStoreName> storeSalesWithStoreNameList = managerMapper.queryStoreSalesInOneDay(start, end);
        // 得到总营业额
        BigDecimal totalRevenue = managerMapper.getTotalRevenue(start, end);
        // 返回结果
        return buildStoreSalesVO(start, end, totalRevenue, storeSalesWithStoreNameList);
    }

    /**
     * 查询一段时间的用户数量和注册用户数量
     *
     * @param day
     * @return
     */
    @Override
    public UserCountVO queryUserCount(Integer day) {
        // 得到对应的枚举类
        StoreSalesRangeType storeSalesRangeType = StoreSalesRangeType.getStoreSalesRangeType(day);
        Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
        // 得到迭代器
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();
        Integer allUserCount = managerMapper.queryAllUserCountInOneDay();
        List<Integer> registerUserCount = managerMapper.queryRegisterUserCountInOneDay(start, end);
        return buildUserCountVO(start, end, allUserCount, registerUserCount);
    }

    /**
     * 计算时间范围
     *
     * @param storeSalesRangeType
     * @return
     */
    /*private Map<LocalDate, LocalDate> calculateDateRange(StoreSalesRangeType storeSalesRangeType) {
        HashMap<LocalDate, LocalDate> map = new HashMap<>();
        // 得到今天
        LocalDate now = LocalDate.now();
        // 得到时间范围的开始
        LocalDate start;
        // 得到时间范围的结束
        LocalDate end;
        if (!isThisWeekOrMonth(storeSalesRangeType)) {
            // 如果不是本周或本月
            // 得到开始时间
            start = adjustDateByDay(now, storeSalesRangeType.getDay());
            // 得到昨天
            end = now.plusDays(-1);
            // 得到用户数量
        } else {
            // 如果是本周或本月
            // 如果是本周
            if (storeSalesRangeType.getDay().equals(StoreSalesRangeType.THIS_WEEK.getDay())) {
                // 得到本周周一
                start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                // 得到本周周末
                end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            } else {
                // 如果是本月
                // 得到本月第一天
                start = now.with(TemporalAdjusters.firstDayOfMonth());
                // 得到本月最后一天
                end = now.with(TemporalAdjusters.lastDayOfMonth());
            }

        }
        map.put(start, end);
        return map;
    }*/
    // 简化
    private Pair<LocalDate, LocalDate> calculateDateRange(StoreSalesRangeType storeSalesRangeType) {
        // 得到今天
        LocalDate now = LocalDate.now();
        // 得到时间范围的开始
        LocalDate start;
        // 得到时间范围的结束
        LocalDate end;
        if (!isThisWeekOrMonth(storeSalesRangeType)) {
            // 如果不是本周或本月
            // 得到开始时间
            start = adjustDateByDay(now, storeSalesRangeType.getDay());
            // 得到昨天
            end = now.plusDays(-1);
            // 得到用户数量
        } else {
            // 如果是本周或本月
            // 如果是本周
            if (storeSalesRangeType.getDay().equals(StoreSalesRangeType.THIS_WEEK.getDay())) {
                // 得到本周周一
                start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                // 得到本周周末
                end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            } else {
                // 如果是本月
                // 得到本月第一天
                start = now.with(TemporalAdjusters.firstDayOfMonth());
                // 得到本月最后一天
                end = now.with(TemporalAdjusters.lastDayOfMonth());
            }

        }
        return Pair.of(start, end);
    }

    /**
     * 构建UserCountVO
     *
     * @param start
     * @param end
     * @param allUserCount
     * @param registerUserCount
     * @return
     */
    private UserCountVO buildUserCountVO(LocalDate start, LocalDate end, Integer allUserCount, List<Integer> registerUserCount) {
        return UserCountVO.builder()
                .userCount(allUserCount)
                .registerUserCount(registerUserCount)
                .start(start)
                .end(end)
                .build();
    }

    /**
     * 判断是本周还是本月
     *
     * @param type
     * @return
     */
    private boolean isThisWeekOrMonth(StoreSalesRangeType type) {
        return type.getDay().equals(StoreSalesRangeType.THIS_WEEK.getDay()) ||
                type.getDay().equals(StoreSalesRangeType.THIS_MONTH.getDay());
    }

    /**
     * 根据天数调整时间
     *
     * @param now
     * @param day
     * @return
     */

    private LocalDate adjustDateByDay(LocalDate now, Integer day) {
        return now.plusDays(day);
    }

    /**
     * 构建StoreSalesVO
     *
     * @param start
     * @param end
     * @param totalRevenue
     * @param storeSalesWithStoreNameList
     * @return
     */
    private StoreSalesVO buildStoreSalesVO(LocalDate start, LocalDate end, BigDecimal totalRevenue, List<StoreSalesWithStoreName> storeSalesWithStoreNameList) {
        return StoreSalesVO.builder()
                .totalRevenue(totalRevenue)
                .storeSalesList(storeSalesWithStoreNameList)
                .start(start)
                .end(end)
                .build();
    }
}


