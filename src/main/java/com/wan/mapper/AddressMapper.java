package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.entity.Address;
import com.wan.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * 根据用户id查询地址
     * @param userId
     *
     * @return
     */
    @Select("select *from address_book where user_id = #{userId} and is_default = #{isDefault}")
    Address getAddressByUserId(Long userId, Integer isDefault);

    /**
     * 修改默认地址
     * @param addressList
     */
    @AutoFill(OperationType.UPDATE)
    void updateDefaultAddress(Address addressList);

    /**
     * 添加地址
     * @param address
     */
    @AutoFill(OperationType.INSERT)
    void insertAddress(Address address);
}
