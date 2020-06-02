package top.linruchang.adv_service.service;


import com.github.pagehelper.PageInfo;
import top.linruchang.domain.PmsProductAdv;
import top.linruchang.domain.QueryParam;

/**
 * @Classname AdvService
 * @Description
 * @Date 2020/3/10 21:37
 * @Created by lrc
 */
public interface AdvService {

    public PageInfo<PmsProductAdv> findAllAdvs(QueryParam queryParam);
}
