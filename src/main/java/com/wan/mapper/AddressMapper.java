package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.entity.Address;
import com.wan.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * 根据用户id查询地址
     *
     * @param userId
     * @param isDefault
     * @return
     */

    List<Address> getAddressByUserId(Long userId, Integer isDefault);

    /**
     * 修改默认地址
     *
     * @param address
     */
    @AutoFill(OperationType.UPDATE)
    void updateDefaultAddress(Address address);

    /**
     * 添加地址
     *
     * @param address
     */
    @AutoFill(OperationType.INSERT)
    void insertAddress(Address address);

    /**
     * 批量修改地址信息
     *
     * @param addressList
     */
    @AutoFill(OperationType.UPDATE)
    void updateBatch(List<Address> addressList);

    @AutoFill(OperationType.INSERT)
    void insertStoreAddress(Address address);

    /**
     * 查找地址
     *
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    Address findAddressById(Long id);

    /**
     * 删除地址
     *
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteAddress(Long id);
}
