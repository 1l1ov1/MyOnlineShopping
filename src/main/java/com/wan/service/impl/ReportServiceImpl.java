package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.MessageConstant;
import com.wan.dto.ReportPageQueryDTO;
import com.wan.entity.Comment;
import com.wan.entity.Report;
import com.wan.exception.ReportException;
import com.wan.mapper.CommentMapper;
import com.wan.mapper.ReportMapper;
import com.wan.result.PageResult;
import com.wan.service.ReportService;
import com.wan.vo.ReportPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageResult pageQueryReport(ReportPageQueryDTO reportPageQueryDTO) {
        // PageHelper.startPage(reportPageQueryDTO.getPage(), reportPageQueryDTO.getPageSize());
        //
        // Page<ReportPageQueryVO> page = reportMapper.pageQuery(reportPageQueryDTO);
        //
        // return PageResult.builder()
        //         .total(page.getTotal())
        //         .data(page.getResult())
        //         .build();
        return reportPageQueryDTO.executePageQuery(reportMapper::pageQuery, reportPageQueryDTO);
    }


    /**
     * 批量删除举报
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteReport(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ReportException(MessageConstant.REPORT_IS_NOT_EXIST);
        }

        try {
            // 更新评论的举报数量
            updateCommentsReportCount(ids);
            reportMapper.batchDeleteReport(ids);
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 更新相关评论的举报计数
     *
     * @param ids 报告ID列表
     */
    private void updateCommentsReportCount(List<Long> ids) {
        // 查询整个举报
        List<Report> reportList = reportMapper.findReports(ids);
        Map<Long, Comment> commentsMap = loadCommentsMap(reportList.stream()
                .map(Report::getCommentId)
                .collect(Collectors.toList()));

        reportList.forEach(report -> {
            // 从map集合中查询评论
            Comment comment = commentsMap.get(report.getCommentId());
            // 如果不空
            if (comment != null) {
                // 直接更新评论对象，无需单独查询每个评论
                comment.setReportCount(comment.getReportCount() - 1);
                commentMapper.updateComment(comment);
            }
        });
    }

    /**
     * 批量加载评论到Map中，以便高效查找
     *
     * @param commentIds 评论ID列表
     * @return 评论ID到评论对象的映射
     */
    private Map<Long, Comment> loadCommentsMap(List<Long> commentIds) {
        // 查询评论列表
        List<Comment> commentsList = commentMapper.findComments(commentIds);
        // 封装到map中
        return commentsList.stream()
                .collect(Collectors.toMap(Comment::getId, comment -> comment));
    }

}
