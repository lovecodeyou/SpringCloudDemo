package top.linruchang.auth_service.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.linruchang.auth_service.domain.Auth;
import top.linruchang.auth_service.domain.AuthMessage;
import top.linruchang.auth_service.service.AuthService;
import top.linruchang.common.JsonModel;
import top.linruchang.common.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname AuthController
 * @Description
 * @Date 2020/2/17 0:28
 * @Created by lrc
 */
@Controller
@RequestMapping
public class AuthController {

    @Autowired
    AuthService authService;

    //用户登录
    @PostMapping(value = "login")
    @ResponseBody
    public JsonModel login(Auth auth, HttpServletResponse resp) {


        JsonModel jsonModel = new JsonModel();

        //获取登录成功信息以及Token
        //如果登录不成功，什么都没有
        AuthMessage authMessage = authService.auth(auth);

        if(auth.getType() == null) {
            if (authMessage == null) {
                jsonModel.setMessage("密码或者账号错误");
                jsonModel.setEffective(false);
            } else {
                jsonModel.setMessage("登录成功");
                jsonModel.setEffective(true);
                jsonModel.setData(authMessage);
            }
        }else if( auth.getType() == 0) {
            if (authMessage == null) {
                jsonModel.setMessage("可能验证码填写错误或者验证码已经失效！!");
                jsonModel.setEffective(false);
            } else {
                jsonModel.setMessage("登录成功");
                jsonModel.setEffective(true);
                jsonModel.setData(authMessage);
            }
        }


        return jsonModel;
    }





    @GetMapping("/mailVerificationCode/{type}/{to}")
    @ResponseBody
    public JsonModel sendVerificationCode(@PathVariable("type") Integer type ,@PathVariable("to") String to) {

        JsonModel jsonModel = new JsonModel();

        if(type == 0) {
            //取得验证码在数据库的ID
            Integer id = authService.sendEmail(to);
            if(id > 0) {

                //加密id
                String encryId = DigestUtils.md5Hex(id.toString());
                jsonModel.setData(encryId);
                jsonModel.setEffective(true);
                jsonModel.setMessage("邮件发送成功！注意查收");
            }else {
                jsonModel.setEffective(false);
                jsonModel.setMessage("可能出错了哦，请重新发送邮件！");
            }

        }else {

        }

        return jsonModel;

    }


    //解析Token
    @GetMapping("info")
    @ResponseBody
    public JsonModel parseToken(@CookieValue("token") String token) {
        String key = "userId";

        JsonModel jsonModel = new JsonModel();

        String info = authService.info(token, key);

        jsonModel.setMessage("userId = " + info);
        jsonModel.setEffective(true);
        return jsonModel;
    }


}
