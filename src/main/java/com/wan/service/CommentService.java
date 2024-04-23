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

    /**
     * 隐藏评论
     * @param id
     * @param commentStatus
     * @return 返回评论商店id
     */
    Long hiddenComment(Long id, Integer commentStatus);
}
