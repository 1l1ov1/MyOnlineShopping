package com.wan.service;

import com.wan.entity.Address;

import java.util.List;

public interface AddressService {
    /**
     * 根据用户id查询地址
     * @param userId
     * @return
     */
    Address getAddressByUserId(Long userId, Integer isDefault);

    /**
     * 查询用户的所有地址
     * @param userId
     * @return
     */
    List<Address> getAllAddressByUserId(Long userId);

    /***
     * 批量修改地址信息
     * @param addressList
     */
    void update(List<Address> addressList);

    /**
     * 设置为默认地址
     * @param address
     */
    void update(Address address);
}
