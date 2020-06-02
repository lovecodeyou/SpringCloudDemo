package top.linruchang.member_service.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.plugins.Docket;
import top.linruchang.common.JsonModel;
import top.linruchang.domain.QueryParam;
import top.linruchang.domain.UmsUserAuthcode;
import top.linruchang.member_service.dao.MemberDao;
import top.linruchang.member_service.domain.Member;
import top.linruchang.member_service.domain.ReceiverParam;
import top.linruchang.member_service.domain.UmsAreaCode;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;
import top.linruchang.member_service.service.MemberService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Classname memberController
 * @Description
 * @Date 2020/2/13 14:44
 * @Created by lrc
 */

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    RestTemplate restTemplate;



    @GetMapping("test")
    @ResponseBody
    public JsonModel getTest() {



        JsonModel jsonModel = restTemplate.getForObject("http://productService/products/1", JsonModel.class);

        return jsonModel;

    }



    //用户登录
    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    public JsonModel login(@RequestParam("loginName") String loginName, @RequestParam("password")String password) {


        Member member = memberService.login(loginName, password);

        JsonModel jsonModel = new JsonModel();
        if(member != null) {
            jsonModel.setData(member);
            jsonModel.setEffective(true);
            jsonModel.setMessage("登录成功");
        }else {
            jsonModel.setData(null);
            jsonModel.setEffective(false);
            jsonModel.setMessage("登录失败，请重新登录");
        }

        return jsonModel;
    }


    @PostMapping(value="/emailLogin")
    @ResponseBody
    public JsonModel emailLogin(@RequestParam("loginName") String loginName, @RequestParam("email")String email, @RequestParam("verificationCode")String verificationCode) {

        JsonModel jsonModel = new JsonModel();

        Member member  = memberService.emailLogin(loginName, email, verificationCode);

        if(member != null){
            jsonModel.setData(member);
            jsonModel.setEffective(true);
            jsonModel.setMessage("登录成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("登录失败");
        }
        return jsonModel;
    }



    //用户注册
    @RequestMapping(value = "/register", method=RequestMethod.POST)
    @ResponseBody
    public JsonModel register(Member member, UmsUserAuthcode authcode, @RequestParam("verificationCodeID") String verificationCodeID) {

        authcode.setTo(member.getEmail());

        boolean isValid = memberService.verificationCodeValid(authcode, verificationCodeID);

        JsonModel jsonModel = new JsonModel();

        //邮箱验证码符合要求
        if(isValid) {
            boolean isSuccess = memberService.register(member);
            if(isSuccess == true) {
                jsonModel.setEffective(true);
                jsonModel.setMessage("注册成功");
            }else {
                jsonModel.setEffective(false);
                jsonModel.setMessage("注册失败，大概率账号已经被注册过哦！");
            }

            //邮箱验证码不符合要求
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("注册失败，请填写正确的验证码或者验证码已经失效！");
        }

        return jsonModel;

    }


    //获取所有用户
    @GetMapping("")
    @ResponseBody
    public JsonModel findAllMembers(QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();


        PageInfo<Member> pageInfo= memberService.findAllMembers(queryParam);

        List<Member> members = pageInfo.getList();

        if(members == null || members.size() == 0) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("获取用户列表数据失败");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取用户列表数据成功");
        }

        return jsonModel;

    }


    //更新用户信息 - id
    @PutMapping("")
    @ResponseBody
    public JsonModel updateMemberById(@RequestBody Member member) {

        JsonModel jsonModel = new JsonModel();

        boolean flag = memberService.updateMemberById(member);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("更新用户信息成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("更新用户信息失败");
        }

        return jsonModel;
    }

    //删除用户信息
    @DeleteMapping("/{user_id}")
    @ResponseBody
    public JsonModel deleteByMemberId(@PathVariable("user_id") Integer user_id){

        JsonModel jsonModel = new JsonModel();

        boolean flag = memberService.deleteByMemberId(user_id);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("删除用户信息成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("删除用户信息失败");
        }
        return jsonModel;
    }


    //获取用户收货地址 - 根据地址ID号
    @GetMapping("/address")
    @ResponseBody
    public UmsUserReceiveAddress findByAddressId(@RequestParam("address_id") Integer address_id) {

        UmsUserReceiveAddress address = memberService.findByAddressId(address_id);

        return address;

    }


    //获取某个用户的所有收货地址
    @GetMapping("/address/{userId}")
    @ResponseBody
    public JsonModel findAddress(@PathVariable("userId") Integer user_id) {

        JsonModel jsonModel = new JsonModel();

        List<UmsUserReceiveAddress> address = memberService.findAddressByUserId(user_id);

        if(address == null || address.size() == 0) {
            jsonModel.setMessage("获取失败：可能你并没有添加地址在列表中");
            jsonModel.setEffective(false);
        }else {
            jsonModel.setData(address);
            jsonModel.setEffective(true);
        }
        return jsonModel;

    }


    //删除用户的收货地址
    @DeleteMapping("/address/{addressIds}")
    @ResponseBody
    public  JsonModel deleteAddress(@PathVariable("addressIds") String addressIds) {
        JsonModel jsonModel = new JsonModel();
        Integer[] ids = null;

        if( addressIds != null) {
            String[] str_ids = addressIds.split(",");
            ids = new Integer[str_ids.length];
            int index = 0;
            for(String str_id : str_ids) {
                ids[index] = Integer.valueOf(str_id);
                index++;
            }

        }

        if(ids == null) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("地址删除不成功：请重新提交删除的地址ID");
        }else {
            boolean flag = memberService.deleteAddressByIds(ids);
            if(flag == true) {
                jsonModel.setEffective(true);
                jsonModel.setMessage("成功删除Id为" + Arrays.toString(ids) + "的地址");
            }else {
                jsonModel.setEffective(false);
                jsonModel.setMessage("地址删除不成功：请重新删除");
            }
        }






        return jsonModel;

    }


    //更改用户的默认地址
    @PutMapping("/address/default")
    @ResponseBody
    public JsonModel changDefaultAddress(@RequestBody ReceiverParam param) {
        JsonModel jsonModel = new JsonModel();

        boolean flag = memberService.changDefaultAddress(param.getHistoryDefaultAddressId(), param.getNewDefaultAddressId());

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("默认地址修改成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("修改默认地址失败：请重新尝试一次");
        }
        return jsonModel;
    }


    //添加用户的收货地址 - 接口2 - 区域ID编码
    @PostMapping("/address2")
    @ResponseBody
    public JsonModel addAddress2(@RequestBody UmsUserReceiveAddress address) {
        JsonModel jsonModel = new JsonModel();

        boolean flag = memberService.addAddress2(address);

        if(flag) {
            jsonModel.setMessage("添加地址成功");
            jsonModel.setEffective(true);
        }else {
            jsonModel.setMessage("添加地址失败：请重新提交");
            jsonModel.setEffective(false);
        }

        return jsonModel;
    }


    //添加用户的收货地址 - 接口1 - 区域名
    @PostMapping("/address")
    @ResponseBody
    public JsonModel addAddress(@RequestBody UmsUserReceiveAddress address) {
        JsonModel jsonModel = new JsonModel();

        boolean flag = memberService.addAddress(address);

        if(flag) {
            jsonModel.setMessage("添加地址成功");
            jsonModel.setEffective(true);
        }else {
            jsonModel.setMessage("添加地址失败：请重新提交");
            jsonModel.setEffective(false);
        }

        return jsonModel;
    }


    //修改用户收获地址的信息 - 根据地址ID
    @PutMapping("/address/{address_id}")
    @ResponseBody
    public JsonModel updateAddress(@PathVariable("address_id") Integer address_id, @RequestBody UmsUserReceiveAddress umsUserReceiveAddress) {
        JsonModel jsonModel = new JsonModel();

        umsUserReceiveAddress.setId(address_id);

        boolean flag = memberService.updateAddressByAddressId(umsUserReceiveAddress);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("修改ID为" + address_id + "地址成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("修改ID为" + address_id + "地址失败");
        }
        return jsonModel;

    }


    //获取省市区信息 - 或者级联
    @GetMapping("/areaCode/{parent_id}")
    @ResponseBody
    public JsonModel getAreaCodes(@PathVariable("parent_id") Integer parentId) {
        JsonModel jsonModel = new JsonModel();

        List<UmsAreaCode> areaCodes = memberService.findByParentId(parentId);

        if(areaCodes == null || areaCodes.size() == 0) {
            jsonModel.setMessage("查询地址失败：请重新发送");
            jsonModel.setEffective(false);
        }else {
            jsonModel.setMessage("查询地址成功");
            jsonModel.setEffective(true);
            jsonModel.setData(areaCodes);
        }

        return jsonModel;
    }


}
