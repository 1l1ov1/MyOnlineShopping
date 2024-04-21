package com.wan.service;

import com.wan.entity.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 添加评论
     * @param comment
     */
    void addComment(Comment comment);

    /**
     * 删除评论
     * @param ids
     */
    void batchDeleteComment(List<Long> ids);


    /**
     * 修改评论
     * @param comment
     */
    void updateComment(Comment comment);
}
