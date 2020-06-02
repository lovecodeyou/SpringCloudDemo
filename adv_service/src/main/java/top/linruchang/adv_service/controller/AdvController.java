package top.linruchang.adv_service.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.linruchang.adv_service.service.AdvService;
import top.linruchang.common.JsonModel;
import top.linruchang.domain.PmsProductAdv;
import top.linruchang.domain.QueryParam;

import java.util.List;

/**
 * @Classname AdvController
 * @Description
 * @Date 2020/3/10 21:11
 * @Created by lrc
 */

@Controller
@RequestMapping("advs")
public class AdvController {

    @Autowired
    AdvService advService;

    //获取所有广告信息
    @RequestMapping(value="", method = RequestMethod.GET)
    @ResponseBody
    public JsonModel findAdvAll(QueryParam queryParam) {
        PageInfo<PmsProductAdv> pageInfo = advService.findAllAdvs(queryParam);

        List<PmsProductAdv> advs = pageInfo.getList();

                JsonModel jsonModel = new JsonModel();
        if(advs == null || advs.size() == 0) {
            jsonModel.setMessage("无广告");
            jsonModel.setEffective(false);
            jsonModel.setData(null);
        }else {
            jsonModel.setMessage("获取数据成功");
            jsonModel.setEffective(true);
            jsonModel.setData(pageInfo);
        }
        return jsonModel;
    }

}
