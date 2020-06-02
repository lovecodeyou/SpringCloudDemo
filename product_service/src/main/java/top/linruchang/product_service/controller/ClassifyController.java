package top.linruchang.product_service.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.linruchang.common.JsonModel;
import top.linruchang.product_service.domain.PmsProductClassify;
import top.linruchang.product_service.domain.QueryParam;
import top.linruchang.product_service.service.ClassifyService;

import java.util.List;

/**
 * @Classname ClassifyController
 * @Description
 * @Date 2020/3/10 22:20
 * @Created by lrc
 */
@Controller
@RequestMapping("classifies")
public class ClassifyController {

    @Autowired
    ClassifyService classifyService;

    //获取首页选项条活动信息
    @RequestMapping(value="", method = RequestMethod.GET)
    @ResponseBody
    public JsonModel findAll(QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();


        PageInfo<PmsProductClassify> classies = classifyService.findAll(queryParam);


        if(classies.getList() == null || classies.getList().size() == 0) {
            jsonModel.setMessage("获取活动选项条失败");
        }else {
            jsonModel.setData(classies);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取活动选项条成功");
        }
        return jsonModel;
    }

}
