package top.linruchang.member_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.domain.QueryParam;
import top.linruchang.domain.UmsUserAuthcode;
import top.linruchang.member_service.domain.Member;

import java.util.Date;
import java.util.List;

/**
 * @Classname MemberDao
 * @Description
 * @Date 2020/2/13 14:52
 * @Created by lrc
 */

@Mapper
public interface MemberDao {

    public Member login(@Param("loginName")String loginName, @Param("password") String password);

    public Member emailLogin(@Param("loginName")String loginName, @Param("email")String email, @Param("verificationCode")String verificationCode, @Param("current_time") Date current_time);

    public Integer register(Member member);

    public Integer isRegister(Member member) ;

    public List<Member> findAllMembers(QueryParam queryParam);

    public Integer updateMemberById(Member member);

    public Integer deleteByMemberId(Integer id);

    public List<UmsUserAuthcode> verificationCodeValid(UmsUserAuthcode umsUserAuthcode);

}
