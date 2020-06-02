package top.linruchang.auth_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.linruchang.auth_common.JWTUtil;
import top.linruchang.auth_service.client.MemberServiceClient;
import top.linruchang.auth_service.config.SysConfig;
import top.linruchang.auth_service.dao.AuthCodeDao;
import top.linruchang.auth_service.domain.Auth;
import top.linruchang.auth_service.domain.AuthMessage;
import top.linruchang.auth_service.service.AuthService;
import top.linruchang.common.JsonModel;
import top.linruchang.common.RandomUtil;
import top.linruchang.domain.UmsUserAuthcode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AuthServiceImpl
 * @Description
 * @Date 2020/2/17 0:30
 * @Created by lrc
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    MemberServiceClient memberServiceClient;

    @Autowired
    JavaMailSender sender;

    @Autowired
    AuthCodeDao authCodeDao;


    @Autowired
    SysConfig sysConfig;

    @Override
    public AuthMessage auth(Auth auth) {

        JsonModel jsonModel = null;

        //1. 从用户服务获取用户信息 - 账号、密码、昵称、邮箱等等
        if(auth.getType() == null) {
            jsonModel = memberServiceClient.login(auth.getLoginName(), auth.getPassword());
        }else if(auth.getType() == 0) {
            jsonModel = memberServiceClient.emailLogin(auth.getLoginName(), auth.getEmail(), auth.getVerification_code());
        }


        if(jsonModel.getData() == null) {
            return null;
        }else {

            Map member = (Map) jsonModel.getData();
            Integer userId = (Integer) member.get("id");
            String loginName = (String) member.get("loginName");
            String nickname = (String) member.get("nickname");
            String headImg = (String)member.get("headImg");
            Integer role_id = (Integer)member.get("role_id");

            Map messgs = new HashMap();
            messgs.put("userId", userId);
            messgs.put("loginName", loginName);
            AuthMessage authMessage = new AuthMessage();

            //1. 获取Token，userId与用户账号进行构成Token负载部分
            // 2. Token有效期为1天，时间一过就重新进行登录，获取新的Token
            //3. 经过服务器的私钥进行签名
            String token = JWTUtil.generateToken(messgs,sysConfig.getPrivateKey(), sysConfig.getExpire());
            authMessage.setToken(token);
            authMessage.setUser_id(userId);
            authMessage.setHeadImg(headImg);
            authMessage.setNickname(nickname);
            authMessage.setRole_id(role_id);
            authMessage.setLoginName(loginName);
            return authMessage;
        }

    }

    @Override
    public String info(String token, String key) {

        String infoFromToken = JWTUtil.getInfoFromToken(token, key, sysConfig.getPublicKey());

        return infoFromToken;
    }

    //发送邮箱验证码 - 进行登录、注册
    @Override
    public Integer sendEmail(String receiverEmail) {


        // 0. 获取邮件的发送地址、以及发送的验证码
        String senderEmail = sysConfig.getSenderEmailAddress();
        String randomStr = RandomUtil.getVerificationCode();

        //1. 配置发送邮件参数
        SimpleMailMessage sme = new SimpleMailMessage();
        sme.setFrom(senderEmail);
        sme.setTo(receiverEmail);
        sme.setSubject("水果电商登录验证码");
        sme.setSentDate(new Date());
        sme.setText(randomStr);

        //2. 发送邮件
        sender.send(sme);

        //3. 将验证码插入到验证表中 - 将验证码信息存储到数据库中。
        // 用户登录、注册的时候需要从数据库中比对验证码是否正确，如果不正确
        // 不给用户进行登陆或者注册
        UmsUserAuthcode umsUserAuthcode = new UmsUserAuthcode();
        umsUserAuthcode.setFrom(senderEmail);
        umsUserAuthcode.setTo(receiverEmail);
        umsUserAuthcode.setType(0);
        umsUserAuthcode.setVerification_code(randomStr);
        Integer flag = authCodeDao.addAuthCode(umsUserAuthcode);


        //4. 返回验证码插入在数据库中的ID号
       return umsUserAuthcode.getId();

    }
}
