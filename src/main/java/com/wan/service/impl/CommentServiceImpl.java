package com.wan.service.impl;

import com.wan.constant.CommentConstant;
import com.wan.constant.MessageConstant;
import com.wan.entity.Comment;
import com.wan.exception.CommentException;
import com.wan.mapper.CommentMapper;
import com.wan.mapper.ReportMapper;
import com.wan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReportMapper reportMapper;
    @Override
    public void addComment(Comment comment) {

    }

    /**
     * 批量删除评论
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteComment(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CommentException(MessageConstant.COMMENT_IS_NOT_EXIST);
        }
        // 批量删除
        commentMapper.batchDeleteComment(ids);

        // 然后根据评论id删除对应的举报
        reportMapper.batchDeleteReportByCommentId(ids);
    }

    @Override
    public void updateComment(Comment comment) {

    }

    /**
     * 隐藏评论
     *
     * @param id
     */
    @Override
    public Long hiddenComment(Long id, Integer commentStatus) {
        // 现根据id得到评论
        Comment comment = commentMapper.findCommentById(id);

        if (ObjectUtils.isEmpty(comment)) {
            // 如果评论为空
            throw new CommentException(MessageConstant.COMMENT_IS_NOT_EXIST);
        }

        // 判断状态是否合法
        if (commentStatus < CommentConstant.NORMAL || commentStatus > CommentConstant.HIDDEN) {
            // 如果状态不合法
            throw new CommentException(MessageConstant.COMMENT_STATUS_IS_INVALID);
        }
        // 如果评论不空，设为对应状态
        comment.setCommentStatus(commentStatus);
        // 修改评论
        commentMapper.updateComment(comment);

        return comment.getStoreId();
    }
}
