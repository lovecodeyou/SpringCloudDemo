package top.linruchang.member_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.domain.QueryParam;
import top.linruchang.domain.UmsUserAuthcode;
import top.linruchang.member_service.dao.AddressDao;
import top.linruchang.member_service.dao.AreaCodeDao;
import top.linruchang.member_service.domain.Member;
import top.linruchang.member_service.dao.MemberDao;
import top.linruchang.member_service.domain.UmsAreaCode;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;
import top.linruchang.member_service.service.MemberService;

import java.util.Date;
import java.util.List;

/**
 * @Classname MemberServiceImpl
 * @Description
 * @Date 2020/2/13 14:50
 * @Created by lrc
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    AddressDao addressDao;


    @Autowired
    AreaCodeDao areaCodeDao;

    @Override
    public Member login(String loginName, String password) {
        return memberDao.login(loginName, password);
    }


    @Override
    public Member emailLogin(String loginName, String email, String verificationCode) {

        return memberDao.emailLogin(loginName, email, verificationCode, new Date());
    }


    @Override
    public boolean register(Member member) {

        if(isRegister(member)) {
            return false;
        }


        Integer num = memberDao.register(member);
        if (num > 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean verificationCodeValid(UmsUserAuthcode umsUserAuthcode, String verificationCodeID){

        List<UmsUserAuthcode> umsUserAuthcodes = memberDao.verificationCodeValid(umsUserAuthcode);

        for(UmsUserAuthcode authCode : umsUserAuthcodes) {
            String encryID = DigestUtils.md5Hex(authCode.getId().toString());

            if(verificationCodeID.equals(encryID)) {
                return true;
            }
        }

        return false;
    }

    //验证账号是否已经被注册
    public Boolean isRegister(Member member) {

        Integer flag = memberDao.isRegister(member);
        if (flag > 0) {
            return true;
        }
        return false;
    }


    @Override
    public PageInfo<Member> findAllMembers(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();
        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Member> members = memberDao.findAllMembers(queryParam);

        PageInfo<Member> pageInfo = new PageInfo<>(members);

        return pageInfo;
    }


    @Override
    public boolean updateMemberById(Member member) {

        Integer flag = memberDao.updateMemberById(member);

        if(flag>0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByMemberId(Integer id) {

        Integer flag = memberDao.deleteByMemberId(id);

        if(flag>0) {
            return true;
        }
        return false;
    }




    @Override
    public List<UmsUserReceiveAddress> findAddressByUserId(Integer user_id) {
        return addressDao.findByUserId(user_id);
    }

    @Override
    public UmsUserReceiveAddress findByAddressId(Integer id) {
        return addressDao.findById(id);
    }

    @Override
    public boolean deleteAddressByIds(Integer[] addressIds) {
        Integer flag = addressDao.deleteByIds(addressIds);

        if (flag > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changDefaultAddress(Integer historyDefaultAddressId, Integer newDefaultAddressId) {
        Integer flag1 = 1;
        Integer flag2 = addressDao.changDefaultAddress(newDefaultAddressId, 1);

        if (historyDefaultAddressId != null) {
            flag1 = addressDao.changDefaultAddress(historyDefaultAddressId, 0);
        }

        if (flag1 > 0 && flag2 > 0) {
            return true;
        }
        return true;
    }


    @Override
    public boolean addAddress(UmsUserReceiveAddress address) {

        Integer flag1 = addressDao.addAddress(address);
        Integer flag2 = addressDao.changAddressCodes(address);

        if (flag1 > 0 && flag2 > 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean addAddress2(UmsUserReceiveAddress address) {

        Integer flag = addressDao.addAddress2(address);

        if (flag > 0) {
            return true;
        }
        return false;
    }


    @Override
    public List<UmsAreaCode> findByParentId(Integer parentId) {
        return areaCodeDao.findByParentId(parentId);
    }


    @Override
    public Boolean updateAddressByAddressId(UmsUserReceiveAddress umsUserReceiveAddress) {
        Integer flag = addressDao.updateAddressByAddressId(umsUserReceiveAddress);

        if(flag > 0) {
            return true;
        }else {
            return false;
        }

    }

}
