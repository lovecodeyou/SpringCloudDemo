package top.linruchang.product_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.product_service.dao.ClassifyDao;
import top.linruchang.product_service.domain.PmsProductClassify;
import top.linruchang.product_service.domain.QueryParam;
import top.linruchang.product_service.service.ClassifyService;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */


@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    ClassifyDao classifyDao;

    @Override
    public PageInfo<PmsProductClassify> findAll(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();
        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);

        List<PmsProductClassify> pmsProductClassifies = classifyDao.findAll();

        PageInfo<PmsProductClassify> pageInfo = new PageInfo<>(pmsProductClassifies);
        return pageInfo;
    }


}
