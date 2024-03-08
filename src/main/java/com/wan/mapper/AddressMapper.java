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
     * @param isDefault
     * @return
     */

    Address getAddressByUserId(Long userId, Integer isDefault);

    /**
     * 得到用户的所有地址
     * @param userId
     * @return
     */
    @Select("select * from address_book where user_id = #{userId}")
    List<Address> getAllAddressByUserId(Long userId);
    /**
     * 修改默认地址
     * @param address
     */
    @AutoFill(OperationType.UPDATE)
    void updateDefaultAddress(Address address);

    /**
     * 添加地址
     * @param address
     */
    @AutoFill(OperationType.INSERT)
    void insertAddress(Address address);

    /**
     * 批量修改地址信息
     * @param addressList
     */
    @AutoFill(OperationType.UPDATE)
    void updateBatch(List<Address> addressList);
}
