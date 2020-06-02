package top.linruchang.product_service.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.linruchang.common.JsonModel;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;
import top.linruchang.product_service.domain.QueryParam;


/**
 * @Classname MemberServiceClient
 * @Description
 * @Date 2020/4/30 18:28
 * @Created by lrc
 */

@FeignClient("memberService")
public interface MemberServiceClient {

    @RequestMapping(value = "/members/address", method = RequestMethod.GET)
    @ResponseBody
    public UmsUserReceiveAddress findByAddressId(@RequestParam("address_id") Integer address_id);

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    @ResponseBody
    public JsonModel findAllMembers(@RequestParam("pageNum")Integer pageNum ,@RequestParam("pageSize")Integer pageSize);

}
