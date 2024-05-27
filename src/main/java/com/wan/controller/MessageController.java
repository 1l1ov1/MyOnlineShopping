package com.wan.controller;

import com.wan.dto.UserChatDTO;
import com.wan.entity.Message;
import com.wan.result.Result;
import com.wan.service.MessageService;
import com.wan.vo.ChatRoomVO;
import com.wan.vo.UserChatVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/userChat")
@Api("用户聊天")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/query")
    public Result<ChatRoomVO> queryUserChatMessage(@RequestBody UserChatDTO userChatDTO) {
        log.info("查询聊天记录：{}", userChatDTO);
        ChatRoomVO chatRoomVO = messageService.queryMessage(userChatDTO);

        return Result.success(chatRoomVO, "查询成功");
    }


}
