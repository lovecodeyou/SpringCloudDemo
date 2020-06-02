package top.linruchang.auth_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.linruchang.common.JsonModel;
import top.linruchang.member_service.domain.Member;

/**
 * @Classname MemberServiceClient
 * @Description
 * @Date 2020/2/17 0:44
 * @Created by lrc
 */

@FeignClient(value="memberService")
public interface MemberServiceClient {


    @RequestMapping(value="members/login", method= RequestMethod.POST)
    @ResponseBody
    public JsonModel login(@RequestParam("loginName") String loginName, @RequestParam("password")String password);


    @RequestMapping(value="members/emailLogin", method = RequestMethod.POST)
    @ResponseBody
    public JsonModel emailLogin(@RequestParam("loginName") String loginName, @RequestParam("email")String email, @RequestParam("verificationCode")String verificationCode);

}
