package com.wan.service;

import com.wan.dto.*;
import com.wan.entity.*;
import com.wan.result.PageResult;
import com.wan.vo.CommentPageQueryVO;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     *
     * @param userLoginDTO
     */
    void addUser(UserLoginDTO userLoginDTO);

    /**
     * 根据用户id查询
     *
     * @param userId
     * @return
     */
    User getUserById(Long userId);

    /**
     * 修改用户
     *
     * @param user
     */
    User update(User user);

    /**
     * 根据用户id得到用户的详细信息
     *
     * @param id
     * @return
     */
    User getDetail(Long id);

    /**
     * 用户重置密码
     *
     * @param updatePasswordDTO
     */
    void updatePassword(UpdatePasswordDTO updatePasswordDTO);


    void createStore(UserCreateStoreDTO userCreateStoreDTO);

    /**
     * 购买商品
     *
     * @param goodsPurchaseDTO
     */
    void buy(GoodsPurchaseDTO goodsPurchaseDTO);

    /**
     * 查询某种类型的订单
     *
     * @param userId
     * @param target
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageResult queryOneTypeOrders(Long userId, Integer target, Integer currentPage, Integer pageSize);

    /**
     * 申请退款
     *
     * @param id
     */
    void applyRefund(Long id);

    /**
     * 给用户添加地址
     * @param address
     */
    void addAddress(Address address);

    /**
     * 删除非默认地址
     * @param id
     */
    void deleteAddress(Long id);

    /**
     * 用户催单
     * @param reminderDTO
     */
    void reminder(ReminderDTO reminderDTO);

    /**
     * 查询评论
     * @param commentPageQueryDTO
     * @return
     */
    CommentPageQueryVO queryComments(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 添加评论
     * @param comment
     */
    void addComment(Comment comment);

    /**
     * 查询评论行为
     * @param commentActionDTO
     * @return
     */
    List<CommentAction> queryCommentsAction(CommentActionDTO commentActionDTO);

    /**
     * 修改评论行为
     * @param commentAction
     */
    void updateCommentAction(CommentAction commentAction);

    /**
     * 添加举报
     * @param report
     */
    Comment addReport(Report report);
}
