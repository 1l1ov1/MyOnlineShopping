package com.wan.service.impl;

import com.wan.constant.AddressConstant;
import com.wan.entity.Address;
import com.wan.mapper.AddressMapper;
import com.wan.service.AddressService;
import com.wan.utils.ObjectUtils;
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
        // 当管理员要修改用户的信息时

        List<Address> addressByUserId = addressMapper.getAddressByUserId(userId, isDefault);
        if (ObjectUtils.isEmpty(addressByUserId) || addressByUserId.size() == 0) {
            // 如果为空，或者没有
            return null;
        }
        // 否则返回回去
        return addressMapper.getAddressByUserId(userId, isDefault).get(0);
    }

    /**
     * 得到用户的所有地址
     *
     * @param userId
     * @return
     */
    public List<Address> getAllAddressByUserId(Long userId) {
        return addressMapper.getAddressByUserId(userId, null);
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
        List<Address> addressList = addressMapper.getAddressByUserId(userId, null);
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
