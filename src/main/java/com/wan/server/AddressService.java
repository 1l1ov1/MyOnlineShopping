package com.wan.server;

import com.wan.entity.Address;

import java.util.List;

public interface AddressService {
    /**
     * 根据用户id查询地址
     * @param userId
     * @return
     */
    Address getAddressByUserId(Long userId, Integer isDefault);

}
