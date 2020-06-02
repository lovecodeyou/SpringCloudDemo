package top.linruchang.adv_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.adv_service.dao.AdvDao;
import top.linruchang.adv_service.service.AdvService;
import top.linruchang.domain.PmsProductAdv;
import top.linruchang.domain.QueryParam;

import java.util.List;

/**
 * @Classname AdvServiceImpl
 * @Description
 * @Date 2020/3/10 21:37
 * @Created by lrc
 */
@Service
public class AdvServiceImpl implements AdvService {

    @Autowired
    AdvDao advDao;

    @Override
    public PageInfo<PmsProductAdv> findAllAdvs(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();

        Integer pageSize = queryParam.getPageSize();
        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductAdv> advs = advDao.findAllAdvs();
        PageInfo<PmsProductAdv> pageInfo = new PageInfo<>(advs);

        return pageInfo;


    }
}
