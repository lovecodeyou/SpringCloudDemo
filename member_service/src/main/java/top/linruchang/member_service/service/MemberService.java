package top.linruchang.member_service.service;

import com.github.pagehelper.PageInfo;
import top.linruchang.domain.QueryParam;
import top.linruchang.domain.UmsUserAuthcode;
import top.linruchang.member_service.domain.Member;
import top.linruchang.member_service.domain.UmsAreaCode;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;

import java.util.List;

/**
 * @Classname MemberService
 * @Description
 * @Date 2020/2/13 14:49
 * @Created by lrc
 */
public interface MemberService {

    public Member login(String loginName, String password);

    public Member emailLogin(String loginName, String password, String verificationCode);

    boolean register(Member member);

    boolean verificationCodeValid(UmsUserAuthcode umsUserAuthcode, String verificationCodeID);

    public Boolean isRegister(Member member);

    PageInfo<Member> findAllMembers(QueryParam queryParam);


    boolean updateMemberById(Member member);

    boolean deleteByMemberId(Integer id);

    List<UmsUserReceiveAddress> findAddressByUserId(Integer user_id);

    UmsUserReceiveAddress findByAddressId(Integer id);

    boolean deleteAddressByIds(Integer[] addressIds);

    boolean changDefaultAddress(Integer historyDefaultAddressId, Integer newDefaultAddressId);

    boolean addAddress(UmsUserReceiveAddress address);

    boolean addAddress2(UmsUserReceiveAddress address);

    List<UmsAreaCode> findByParentId(Integer parentId);

    Boolean updateAddressByAddressId(UmsUserReceiveAddress umsUserReceiveAddress);
}
