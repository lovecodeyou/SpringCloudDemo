package top.linruchang.product_service.service.impl;


import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.product_service.dao.DetailDao;
import top.linruchang.product_service.domain.PmsProductDetailinfo;
import top.linruchang.product_service.service.DetailService;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */

@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    DetailDao detailDao;

    @Override
    public PmsProductDetailinfo findById(Integer id){
        return detailDao.findById(id);
    }

    @Override
    public Boolean updateProductDetail(PmsProductDetailinfo pmsProductDetailinfo) {

        Integer flag = detailDao.updateProductDetail(pmsProductDetailinfo);

        if(flag > 0) {
            return true;
        }else {
            return false;
        }


    }

}
