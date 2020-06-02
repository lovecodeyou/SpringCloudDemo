package top.linruchang.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;

/**
 * @Classname MemberServiceClient
 * @Description
 * @Date 2020/3/22 0:36
 * @Created by lrc
 */

@FeignClient("memberService")
public interface MemberServiceClient {

    @RequestMapping(value = "/members/address", method = RequestMethod.GET)
    @ResponseBody
    public UmsUserReceiveAddress findByAddressId(@RequestParam("address_id") Integer address_id);

}
