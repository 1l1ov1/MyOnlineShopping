package com.wan.service;

import com.wan.dto.UserChatDTO;
import com.wan.entity.Message;
import com.wan.entity.Store;
import com.wan.vo.ChatRoomVO;
import com.wan.vo.UserChatVO;

import java.util.List;

public interface MessageService {
    /**
     * 发送消息
     * @param message
     */
    void saveMessage(Message message);

    /**
     * 查询消息
     * @param userChatDTO
     * @return
     */
    ChatRoomVO queryMessage(UserChatDTO userChatDTO);

}
