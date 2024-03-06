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
}
