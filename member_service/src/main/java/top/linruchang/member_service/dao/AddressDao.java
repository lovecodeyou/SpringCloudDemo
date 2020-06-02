package top.linruchang.member_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;

import java.util.List;

/**
 * @Classname AddressDao
 * @Description
 * @Date 2020/3/17 21:41
 * @Created by lrc
 */

@Mapper
public interface AddressDao {

    public List<UmsUserReceiveAddress> findByUserId(Integer user_id);

    public UmsUserReceiveAddress findById(Integer id);

    public Integer deleteByIds(@Param("addressIds") Integer[] id);

    public Integer changDefaultAddress(@Param("addressId") Integer addressId, @Param("addressState")Integer state);


    public Integer addAddress(UmsUserReceiveAddress address);

    public Integer addAddress2(UmsUserReceiveAddress address);

    public Integer changAddressCodes(UmsUserReceiveAddress address);

    public Integer updateAddressByAddressId(UmsUserReceiveAddress umsUserReceiveAddress);

}
