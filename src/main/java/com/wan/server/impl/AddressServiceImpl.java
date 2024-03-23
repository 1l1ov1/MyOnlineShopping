package com.wan.server.impl;

import com.wan.constant.AddressConstant;
import com.wan.entity.Address;
import com.wan.mapper.AddressMapper;
import com.wan.server.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     *
     * @param userId
     * @return
     */
    public List<Address> getAllAddressByUserId(Long userId) {
        return addressMapper.getAllAddressByUserId(userId);
    }

    /**
     * 批量修改地址信息
     *
     * @param addressList
     */
    @Override
    public void update(List<Address> addressList) {
        addressMapper.updateBatch(addressList);
    }

    /**
     * 设置为默认地址
     *
     * @param address
     */
    @Override
    public void update(Address address) {
        Long userId = address.getUserId();
        Long id = address.getId();
        // 查找到该用户的所有地址
        List<Address> addressList = addressMapper.getAllAddressByUserId(userId);
        // 更新地址
        List<Address> newAddressList = addressList.stream()
                .peek(item -> {
                    // 如果说id相等
                    if (id.equals(item.getId())) {
                        item.setIsDefault(AddressConstant.IS_DEFAULT);
                    } else {
                        item.setIsDefault(AddressConstant.IS_NOT_DEFAULT);
                    }
                })
                .collect(Collectors.toList());
        addressMapper.updateBatch(newAddressList);
    }
}
