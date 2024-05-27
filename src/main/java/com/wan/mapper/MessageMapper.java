package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.dto.UserChatDTO;
import com.wan.entity.Message;
import com.wan.enumeration.OperationType;
import com.wan.vo.UserChatVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 插入消息
     *
     * @param message
     */
    @AutoFill(OperationType.INSERT)
    void insertMessage(Message message);

    /**
     * 查询聊天消息
     * @param userChatDTO
     * @return
     */
    List<UserChatVO> queryMessage(UserChatDTO userChatDTO);
}
