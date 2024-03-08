package com.wan.server.impl;

import com.wan.entity.Address;
import com.wan.mapper.AddressMapper;
import com.wan.server.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Override
    public Address getAddressByUserId(Long userId, Integer isDefault) {
        return addressMapper.getAddressByUserId(userId, isDefault);
    }

    /**
     * 得到用户的所有地址
     * @param userId
     * @return
     */
    public List<Address> getAllAddressByUserId(Long userId) {
        return addressMapper.getAllAddressByUserId(userId);
    }

    /**
     * 批量修改地址信息
     * @param addressList
     */
    @Override
    public void update(List<Address> addressList) {
        addressMapper.updateBatch(addressList);
    }
}
