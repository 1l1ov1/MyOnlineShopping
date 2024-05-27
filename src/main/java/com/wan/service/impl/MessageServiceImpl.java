package com.wan.service.impl;

import com.wan.constant.ChatConstant;
import com.wan.constant.MessageConstant;
import com.wan.dto.UserChatDTO;
import com.wan.entity.Message;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.exception.MessageException;
import com.wan.mapper.MessageMapper;
import com.wan.mapper.StoreMapper;
import com.wan.mapper.UserMapper;
import com.wan.service.MessageService;
import com.wan.utils.CheckObjectFieldUtils;
import com.wan.utils.ObjectUtils;
import com.wan.vo.ChatRoomVO;
import com.wan.vo.UserChatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 发送消息
     *
     * @param message
     */
    @Override
    public void saveMessage(Message message) {
        if (ObjectUtils.isEmpty(message)) {
            // 如果为空
            throw new MessageException(MessageConstant.MESSAGE_IS_NULL);
        }

        // 如果不空 保存消息
        messageMapper.insertMessage(message);
    }

    @Override
    public ChatRoomVO queryMessage(UserChatDTO userChatDTO) {
        if (ObjectUtils.isEmpty(userChatDTO)) {
            // 如果为空
            throw new MessageException(MessageConstant.MESSAGE_IS_NULL);
        }
        // 如果不空的话就去查询字段
        try {
            if (CheckObjectFieldUtils.allFieldNotNUll(userChatDTO)) {
                // 如果都不空
                // 得到查询类型
                Integer type = userChatDTO.getType();
                // 如果是自己查自己，也就是店家点击私信的
                if (type == ChatConstant.SELF) {
                    return querySelf(userChatDTO);
                } else if (type == ChatConstant.USER) {
                    return queryUser(userChatDTO);
                }

            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 如果空的话
        return ChatRoomVO.builder().build();
    }

    /**
     * 店家查询自己的聊天记录
     *
     * @param userChatDTO
     * @return
     */
    private ChatRoomVO querySelf(UserChatDTO userChatDTO) {
        // 将商家发送的id改为空
        userChatDTO.setSendId(null);

        // 得到消息
        List<UserChatVO> userChatVOS = messageMapper.queryMessage(userChatDTO);
        // 得到那些用户id
        List<Long> userIdList = userChatVOS.stream().map(UserChatVO::getReceiveId)
                .distinct()
                .collect(Collectors.toList());
        // 根据id查询用户
        List<User> user = userMapper.findUserByIds(userIdList);
        // 如果集合为空
        if (CollectionUtils.isEmpty(user)) {
            return ChatRoomVO.builder()
                    .userContactList(Collections.emptyList())
                    .userChatVOList(userChatVOS)
                    .build();
        }
        return ChatRoomVO.builder()
                .userContactList(user)
                .userChatVOList(userChatVOS)
                .build();
    }

    /**
     * 用户查询自己的聊天记录
     *
     * @param userChatDTO
     * @return
     */
    private ChatRoomVO queryUser(UserChatDTO userChatDTO) {
        // 得到所有的对某个商店的聊天
        List<UserChatVO> userChatVOS = messageMapper.queryMessage(userChatDTO);
        // 还要查询接收到的所有消息
        userChatDTO.setSendId(null);
        userChatDTO.setReceiveId(userChatDTO.getSendId());
        userChatVOS.addAll(messageMapper.queryMessage(userChatDTO));
        // 得到该用户所有的联系人
        userChatDTO.setSendId(userChatDTO.getReceiveId());
        userChatDTO.setReceiveId(null);

        // 得到所有的商店id
        List<Long> receiveIds = messageMapper.queryMessage(userChatDTO).stream()
                .map(UserChatVO::getReceiveId)
                .distinct()
                .collect(Collectors.toList());
        // 根据id查询店铺
        if (receiveIds.isEmpty()) {
            return ChatRoomVO.builder()
                    .storeContactList(Collections.emptyList())
                    .userChatVOList(userChatVOS)
                    .build();
        }
        List<Store> storeList = storeMapper.findStoreByIds(receiveIds);

        return ChatRoomVO.builder()
                .storeContactList(storeList)
                .userChatVOList(userChatVOS)
                .build();
    }

}
