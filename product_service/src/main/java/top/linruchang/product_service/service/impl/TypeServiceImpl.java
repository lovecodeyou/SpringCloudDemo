package top.linruchang.product_service.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.domain.QueryParam;
import top.linruchang.product_service.dao.ProductDao;
import top.linruchang.product_service.dao.TypeDao;
import top.linruchang.product_service.domain.PmsProductType;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.service.TypeService;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;

    @Autowired
    ProductDao productDao;

    @Override
    public PageInfo<PmsProductType> findAll(QueryParam queryParam) {


        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();


        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductType> advs = typeDao.findAll();
        PageInfo<PmsProductType> pageInfo = new PageInfo<>(advs);

        return pageInfo;


    }

    @Override
    public PageInfo<PmsProductType> findAllOnlyType(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();


        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductType> allOnlyTypes = typeDao.findAllOnlyType();
        PageInfo<PmsProductType> pageInfo = new PageInfo<>(allOnlyTypes);

        return pageInfo;
    }


    @Override
    public PmsProductType findById(QueryParam queryParam, Integer type_id) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();


        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);


        return typeDao.findById(type_id);


    }


    @Override
    public PageInfo<Product> findById2(QueryParam queryParam, Integer type_id) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();


        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);


        List<Product> products = productDao.findByTypeId(type_id);


        PageInfo<Product> pageInfo = new PageInfo<>(products);

        return pageInfo;

    }
}
