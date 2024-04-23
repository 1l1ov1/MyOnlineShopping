package com.wan.service.impl;

import com.wan.constant.MessageConstant;
import com.wan.constant.OrdersConstant;
import com.wan.constant.UserConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.GoodsSalesDTO;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.enumeration.StoreSalesRangeType;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.StoreException;
import com.wan.mapper.*;
import com.wan.service.StatisticService;
import com.wan.vo.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 查询商店的营业额
     *
     * @param type
     * @return
     */
    @Override
    public StoreSalesVO queryStoreSalesInOneDay(Integer type) {
        try {
            // 得到对应的枚举类型
            StoreSalesRangeType storeSalesRangeType = validateType(type);

            Long storeId;
            // 如果是管理员
            if (isAdministrator()) {
                storeId = null;
            } else {
                // 如果不是管理员
                Long userId = validateUserId();
                // 得到对应的商店
                Store store = validateStore(userId);
                // 得到对应的商店id
                storeId = store.getId();
            }
            // 删除当前线程中的用户ID
            ThreadBaseContext.removeCurrentId();
            Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
            LocalDate start = pair.getLeft();
            LocalDate end = pair.getRight();

            // 查询营业额和总营业额
            List<StoreSalesWithStoreName> storeSalesWithStoreNameList = statisticMapper.queryStoreSalesInOneDay(start, end, storeId);
            BigDecimal totalRevenue = statisticMapper.getTotalRevenue(start, end, storeId);

            // 返回结果
            return buildStoreSalesVO(start, end, totalRevenue, storeSalesWithStoreNameList);
        } catch (Exception e) {
            // 适当的异常处理逻辑
            // 这里可以根据不同异常类型进行不同的处理
            throw new RuntimeException("查询店家销售信息时发生错误", e);
        }
    }

    /**
     * 查询用户订单数量
     *
     * @param day
     * @return
     */

    @Override
    public OrdersUserCountVO queryOrdersUserCount(Integer day) {
        StoreSalesRangeType storeSalesRangeType = validateType(day);
        Long storeId;
        // 如果是管理员
        if (isAdministrator()) {
            storeId = null;
        } else {
            // 如果不是管理员
            Long userId = validateUserId();
            // 得到对应的商店
            Store store = validateStore(userId);
            // 得到对应的商店id
            storeId = store.getId();
        }
        // 删除当前线程中的用户ID
        ThreadBaseContext.removeCurrentId();
        Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();

        Integer totalOrdersCount = statisticMapper.getTotalOrdersCount(start, end, storeId);
        List<StoreSalesAndCategoryName> storeSalesAndCategoryNameList = statisticMapper.getStoreSales(start, end, storeId);
        return builderOrdersUserCountVO(start, end, totalOrdersCount, storeSalesAndCategoryNameList);
    }

    /**
     * 查询订单数量
     *
     * @param day
     * @return
     */
    @Override
    public OrdersCountVO queryOrdersCount(Integer day) {
        StoreSalesRangeType storeSalesRangeType = validateType(day);
        Long storeId;
        // 如果是管理员
        if (isAdministrator()) {
            storeId = null;
        } else {
            // 如果不是管理员
            Long userId = validateUserId();
            // 得到对应的商店
            Store store = validateStore(userId);
            // 得到对应的商店id
            storeId = store.getId();
        }
        // 删除当前线程中的用户ID
        ThreadBaseContext.removeCurrentId();
        Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();
        List<LocalDate> dateRange = new ArrayList<>();
        // 得到总订单数集合
        List<Integer> totalOrdersCountList = new ArrayList<>();
        // 得到成功订单数集合
        List<Integer> successfulOrdersCountList = new ArrayList<>();
        // 得到范围
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            dateRange.add(date);
        }

        for (LocalDate date : dateRange) {
            // 得到指定时间的0时0分0秒
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            // 得到指定时间的23时59分59秒
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            // 得到当天总订单数
            Integer totalOrdersCount = statisticMapper.queryOrdersCount(beginTime, endTime, null, storeId);
            // 得到当天成功订单数
            Integer successfulOrdersCount = statisticMapper.queryOrdersCount(beginTime, endTime, OrdersConstant.SUCCESSFUL_ORDER, storeId);

            totalOrdersCountList.add(totalOrdersCount);
            successfulOrdersCountList.add(successfulOrdersCount);
        }

        Integer totalOrdersCount = totalOrdersCountList.stream().reduce(Integer::sum).get();
        Integer successfulOrdersCount = successfulOrdersCountList.stream().reduce(Integer::sum).get();
        Double ordersCompletionRate = totalOrdersCount == 0 ? 0 : successfulOrdersCount.doubleValue() / totalOrdersCount;
        return builderOrdersCountVO(start, end,
                totalOrdersCountList, successfulOrdersCountList,
                totalOrdersCount, successfulOrdersCount, ordersCompletionRate);
    }

    /**
     * 查询用户数量
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
        List<LocalDate> dateRange = new ArrayList<>();
        // 得到总订单数集合
        List<Integer> userCountList = new ArrayList<>();
        // 得到成功订单数集合
        List<Integer> registerUserCountList = new ArrayList<>();
        // 得到范围
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            dateRange.add(date);
        }

        for (LocalDate date : dateRange) {

            // 得到指定时间的23时59分59秒
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            // 得到当天总订单数
            Integer userCount = statisticMapper.queryUserCountByDay(null, endTime);
            // 得到指定时间的0时0分0秒
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            // 得到当天成功订单数
            Integer registerUserCount = statisticMapper.queryUserCountByDay(beginTime, endTime);

            userCountList.add(userCount);
            registerUserCountList.add(registerUserCount);
        }
        return buildUserCountVO(start, end, userCountList, registerUserCountList);
    }

    /**
     * 查询销量排名前十
     *
     * @param day
     * @return
     */
    @Override
    public SalesTop10VO querySalesTop10(Integer day) {
        // 得到对应的枚举类
        StoreSalesRangeType storeSalesRangeType = StoreSalesRangeType.getStoreSalesRangeType(day);
        Long storeId;
        // 如果是管理员
        if (isAdministrator()) {
            storeId = null;
        } else {
            // 如果不是管理员
            Long userId = validateUserId();
            // 得到对应的商店
            Store store = validateStore(userId);
            // 得到对应的商店id
            storeId = store.getId();
        }
        // 删除当前线程中的用户ID
        ThreadBaseContext.removeCurrentId();
        Pair<LocalDate, LocalDate> pair = calculateDateRange(storeSalesRangeType);
        // 得到迭代器
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();

        List<GoodsSalesDTO> goodsSalesDTOList = statisticMapper.getSalesTop10(start, end, storeId, OrdersConstant.SUCCESSFUL_ORDER);
        List<String> goodsNameList = goodsSalesDTOList.stream()
                .map(GoodsSalesDTO::getGoodsName).collect(Collectors.toList());
        List<Integer> numberList = goodsSalesDTOList.stream()
                .map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        return SalesTop10VO.builder()
                .goodsNameList(goodsNameList)
                .numberList(numberList)
                .build();
    }


    /**
     * 数据导出（30天的）
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        // 得到一个月的日期开始和结束
        Pair<LocalDate, LocalDate> pair = calculateDateRange(StoreSalesRangeType.LAST_30_DAYS);
        LocalDate start = pair.getLeft();
        LocalDate end = pair.getRight();
        // 获得数据
        ExportDataVO exportDataVO = getExportDataVO(start, end);

        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("template/运营数据报表模板.xlsx");


        try {
            // 基于模板创建excel
            XSSFWorkbook excel = new XSSFWorkbook(in);
            // 获得一个Sheet页
            // 获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(1).getCell(1).setCellValue(start + "至" + end);
            // 获得第4行
            XSSFRow row = sheet.getRow(3);
            // 获取单元格
            row.getCell(2).setCellValue(exportDataVO.getTurnover().doubleValue());
            row.getCell(4).setCellValue(exportDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(exportDataVO.getNewUsers());
            // 获取第5行
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(exportDataVO.getSuccessfulOrdersCount());
            row.getCell(4).setCellValue(exportDataVO.getUnitPrice().doubleValue());
            // 填充明细数据
            // lengthOfMonth 返回一个月有多少天
            for (int i = 0; i < start.lengthOfMonth(); i++) {
                LocalDate date = start.plusDays(i);
                // 准备明细数据
                exportDataVO = getExportDataVO(date, date);
                // 获取某一行
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(exportDataVO.getTurnover().doubleValue());
                row.getCell(3).setCellValue(exportDataVO.getSuccessfulOrdersCount());
                row.getCell(4).setCellValue(exportDataVO.getOrderCompletionRate());
                row.getCell(5).setCellValue(exportDataVO.getUnitPrice().doubleValue());
                row.getCell(6).setCellValue(exportDataVO.getNewUsers());
            }
            // 通过输出流将文件下载到客户端浏览器中

            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            // 关闭资源
            out.close();
            in.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据给定的日期范围获取导出数据的视图对象。
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return ExportDataVO 导出数据的视图对象，包含总营收、总订单数、成功订单数、订单完成率、平均客单价和新增用户数。
     */
    private ExportDataVO getExportDataVO(LocalDate start, LocalDate end) {
        Long storeId;
        if (isAdministrator()) {
            // 判断是否为管理员，是则storeId设为null
            storeId = null;
        } else {
            // 验证用户ID的有效性，并获取对应的商店
            Long userId = validateUserId();
            Store store = validateStore(userId);
            // 获取商店ID
            storeId = store.getId();
        }
        // 删除当前线程中的用户ID
        ThreadBaseContext.removeCurrentId();
        // 统计指定日期范围内的总营收  月底
        BigDecimal totalRevenue = statisticMapper.getTotalRevenue(start, end, storeId);
        totalRevenue = totalRevenue == null ? BigDecimal.valueOf(0.0) : totalRevenue;

        // 统计指定日期范围内的总订单数
        Integer totalOrdersCount = statisticMapper.getTotalOrdersCount(start, end, storeId) == null
                ? 0 : statisticMapper.getTotalOrdersCount(start, end, storeId);

        // 统计指定日期范围内成功的订单数
        Integer successfulOrdersCount = statisticMapper
                .queryOrdersCount(LocalDateTime.of(start, LocalTime.MIN),
                        LocalDateTime.of(end, LocalTime.MAX),
                        OrdersConstant.SUCCESSFUL_ORDER, storeId);
        BigDecimal unitPrice = BigDecimal.valueOf(0.0);

        Double orderCompletionRate = 0.0;
        if (totalOrdersCount != 0 && successfulOrdersCount != 0) {
            // 计算订单完成率和平均客单价
            orderCompletionRate = successfulOrdersCount.doubleValue() / totalOrdersCount;
            unitPrice = totalRevenue
                    .divide(BigDecimal.valueOf(successfulOrdersCount), 2, RoundingMode.HALF_UP);
        }

        // 统计指定日期范围内的新增用户数
        Integer newUsers = statisticMapper.queryUserCountByDay(LocalDateTime.of(start, LocalTime.MIN),
                LocalDateTime.of(end, LocalTime.MAX));

        // 构建并返回导出数据的视图对象
        return buildExportDataVO(totalRevenue,
                successfulOrdersCount,
                orderCompletionRate,
                unitPrice,
                newUsers);
    }

    private ExportDataVO buildExportDataVO(BigDecimal totalRevenue,
                                           Integer successfulOrdersCount,
                                           Double orderCompletionRate,
                                           BigDecimal unitPrice,
                                           Integer newUsers) {
        return ExportDataVO.builder()
                .turnover(totalRevenue)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .successfulOrdersCount(successfulOrdersCount)
                .build();
    }

    /**
     * 构建UserCountVO
     *
     * @param start
     * @param end
     * @param userCountList
     * @param registerUserCount
     * @return
     */
    private UserCountVO buildUserCountVO(LocalDate start, LocalDate end,
                                         List<Integer> userCountList,
                                         List<Integer> registerUserCount) {
        return UserCountVO.builder()
                .userCountList(userCountList)
                .registerUserCountList(registerUserCount)
                .start(start)
                .end(end)
                .build();
    }


    private OrdersCountVO builderOrdersCountVO(LocalDate start, LocalDate end,
                                               List<Integer> totalOrdersCountList,
                                               List<Integer> successfulOrdersCountList,
                                               Integer totalOrdersCount,
                                               Integer successfulOrdersCount,
                                               Double ordersCompletionRate) {
        return OrdersCountVO.builder()
                .totalOrdersCountList(totalOrdersCountList)
                .successfulOrdersCountList(successfulOrdersCountList)
                .totalOrdersCount(totalOrdersCount)
                .successfulOrdersCount(successfulOrdersCount)
                .ordersCompletionRate(ordersCompletionRate)
                .start(start)
                .end(end)
                .build();
    }

    /**
     * 返回OrdersUserCountVO对象
     *
     * @param start
     * @param end
     * @param totalOrdersCount
     * @param storeSalesAndCategoryNameList
     * @return
     */
    private OrdersUserCountVO builderOrdersUserCountVO(LocalDate start, LocalDate end, Integer totalOrdersCount, List<StoreSalesAndCategoryName> storeSalesAndCategoryNameList) {
        return OrdersUserCountVO.builder()
                .start(start)
                .end(end)
                .totalUserCount(totalOrdersCount)
                .storeSalesAndCategoryNameList(storeSalesAndCategoryNameList)
                .build();
    }

    private StoreSalesRangeType validateType(Integer type) {
        // 得到对应的枚举类型
        StoreSalesRangeType storeSalesRangeType = StoreSalesRangeType.getStoreSalesRangeType(type);
        if (storeSalesRangeType == null) {
            throw new IllegalArgumentException("查询营业额的类型无效: " + type);
        }
        return storeSalesRangeType;
    }


    /**
     * 检查当前用户是否为管理员。
     * 该方法不接受任何参数，但会内部执行用户身份验证，并基于用户的状态确定其是否为管理员。
     *
     * @return 返回一个布尔值，如果用户是管理员，则返回true；否则，返回false。
     * @throws AccountNotFountException 如果尝试验证的用户不存在，抛出此异常。
     */
    private Boolean isAdministrator() {
        // 验证用户ID的有效性并获取用户ID
        Long userId = validateUserId();
        User user = userMapper.getById(userId);
        // 如果数据库中不存在对应的用户，抛出异常
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.USER_IS_NOT_EXIST);
        }
        Integer status = user.getStatus();
        // 判断用户状态是否为管理员状态
        return status == UserConstant.MANAGER || status == UserConstant.SUPER_ADMINISTRATOR;
    }


    private Long validateUserId() {
        // 得到该商家的用户id
        Long userId = ThreadBaseContext.getCurrentId();

        if (userId == null) {
            throw new AccountNotFountException(MessageConstant.USER_IS_NOT_EXIST);
        }
        return userId;
    }

    /**
     * 验证商店是否存在且合法
     *
     * @param userId
     * @return
     */
    private Store validateStore(Long userId) {
        // 得到对应的商店
        Store store = storeMapper.findStoreByUserId(userId);
        if (store == null) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }
        return store;
    }


    /**
     * 计算特定时间范围内的日期起止点。
     *
     * @param storeSalesRangeType 销售范围类型，定义了计算日期范围的方式（例如本周、本月等）。
     * @return Pair<LocalDate, LocalDate> 一个包含范围开始和结束日期的Pair对象。
     */
    private Pair<LocalDate, LocalDate> calculateDateRange(StoreSalesRangeType storeSalesRangeType) {
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 初始化时间范围的开始和结束日期
        LocalDate start;
        LocalDate end;

        // 根据销售范围类型确定日期范围
        if (storeSalesRangeType.equals(StoreSalesRangeType.THIS_WEEK)
                || storeSalesRangeType.equals(StoreSalesRangeType.THIS_MONTH)) {
            // 对于本周和本月的范围类型，使用特定的逻辑来计算开始和结束日期
            switch (storeSalesRangeType) {
                case THIS_WEEK:
                    start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    break;
                case THIS_MONTH:
                    start = now.with(TemporalAdjusters.firstDayOfMonth());
                    end = now.with(TemporalAdjusters.lastDayOfMonth());
                    break;
                default:
                    // 如果传入了无效的销售范围类型，则抛出异常
                    throw new IllegalArgumentException("Invalid StoreSalesRangeType");
            }
        } else {
            // 对于其他范围类型，根据指定的天数偏移来计算开始和结束日期
            int daysOffset = storeSalesRangeType.getDay();
            start = now.plusDays(daysOffset);
            end = now.minusDays(1);
        }

        // 返回计算出的日期范围
        return Pair.of(start, end);
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
