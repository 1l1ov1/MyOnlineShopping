package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.ReportPageQueryDTO;
import com.wan.entity.Report;
import com.wan.enumeration.OperationType;
import com.wan.vo.ReportPageQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReportMapper {
    /**
     * 查询某天的用户举报次数
     *
     * @param userId
     * @param date
     * @return
     */
    @Select("select count(*) from report where user_id = #{userId} and DATE (create_time) = #{date}")
    Integer findReportCount(Long userId, LocalDate date);

    /**
     * 添加举报
     *
     * @param report
     */
    @AutoFill(OperationType.INSERT)
    void addReport(Report report);

    /**
     * 查看用户是否举报过该评论
     *
     * @param userId
     * @param commentId
     * @return
     */
    @Select("select * from report where user_id = #{userId} and comment_id = #{commentId}")
    Report findReportByUserIdAndCommentId(Long userId, Long commentId);

    /**
     * 分页查询
     *
     * @param reportPageQueryDTO
     * @return
     */
    Page<ReportPageQueryVO> pageQuery(ReportPageQueryDTO reportPageQueryDTO);

    /**
     * 根据id查询举报
     *
     * @param id
     * @return
     */
    @Select("select * from report where id = #{id}")
    Report findReportById(Long id);

    /**
     * 修改举报
     *
     * @param report
     */
    @AutoFill(OperationType.UPDATE)
    void updateReport(Report report);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteReport(List<Long> ids);

    /**
     * 根据评论id批量删除举报
     * @param commentsIds
     */
    void batchDeleteReportByCommentId(List<Long> commentsIds);

    /**
     * 得到ids中的所有举报
     * @param ids
     * @return
     */
    List<Report> findReports(List<Long> ids);
}
