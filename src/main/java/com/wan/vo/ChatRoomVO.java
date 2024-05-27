package com.wan.vo;

import com.wan.entity.Store;
import com.wan.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomVO {
    private List<UserChatVO> userChatVOList;
    private List<Store> storeContactList;
    private List<User> userContactList;
}
