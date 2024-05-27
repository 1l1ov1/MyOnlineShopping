package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.dto.CommentActionDTO;
import com.wan.dto.CommentPageQueryDTO;
import com.wan.entity.Comment;
import com.wan.entity.CommentAction;
import com.wan.enumeration.OperationType;
import com.wan.vo.CommentPageQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 添加评论
     *
     * @param comment
     */
    @AutoFill(OperationType.INSERT)
    void addComment(Comment comment);

    /**
     * 添加评论操作
     *
     * @param commentAction
     */
    @AutoFill(OperationType.INSERT)
    void addCommentAction(CommentAction commentAction);

    /**
     * 删除评论
     *
     * @param ids
     */

    void batchDeleteComment(List<Long> ids);


    /**
     * 修改评论
     *
     * @param comment
     */
    @AutoFill(OperationType.UPDATE)
    void updateComment(Comment comment);

    /**
     * 查询评论
     *
     * @param commentPageQueryDTO
     * @return
     */
    CommentPageQueryVO queryComments(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 查询评论操作
     *
     * @param commentActionDTO
     * @return
     */
    List<CommentAction> findCommentAction(CommentActionDTO commentActionDTO);

    @Select("select * from comment_action where user_id = #{userId} and comment_id = #{commentId}")
    CommentAction findCommentActionByUserIdAndCommentId(Long userId, Long commentId);

    /**
     * 修改评论行为
     *
     * @param commentAction
     */
    @AutoFill(OperationType.UPDATE)
    void updateCommentAction(CommentAction commentAction);

    /**
     * 根据id查询评论
     *
     * @param commentId
     * @return
     */
    @Select("select * from comments where id = #{commentId}")
    Comment findCommentById(Long commentId);

    /**
     * 根据id查询评论
     *
     * @param commentsIdList
     * @return
     */
    List<Comment> findComments(List<Long> commentsIdList);

    /**
     * 查询某用户的所有评论
     * @param userId
     * @return
     */
    @Select("select * from comments where user_id = #{userId}")
    List<Comment> findCommentsByUserId(Long userId);
}
