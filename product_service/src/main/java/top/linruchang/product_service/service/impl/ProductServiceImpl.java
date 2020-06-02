package top.linruchang.product_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.common.JsonModel;
import top.linruchang.member_service.domain.Member;
import top.linruchang.product_service.client.MemberServiceClient;
import top.linruchang.product_service.dao.ProductDao;
import top.linruchang.product_service.domain.PmsProductComment;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;
import top.linruchang.product_service.service.ProductService;

import javax.management.Query;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    MemberServiceClient memberServiceClient;

    @Override
    public PageInfo<Product> findAllProducts(QueryParam queryParam) {

        if(queryParam.getPageNum() == null) {
            PageHelper.startPage(0, 0);
        }else {
            PageHelper.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }

        List<Product> products = productDao.findAllProducts(queryParam);

        PageInfo<Product> pageInfo = new PageInfo<>(products);

        return pageInfo;
    }

    @Override
    public Product findProductById(Integer id) {
        return null;
    }


    @Override
    public Product findByProductId(Integer product_id) {
        return productDao.findByProductId(product_id);
    }


    @Override
    public boolean deleteByProductId(Integer id) {

        Integer flag = productDao.deleteByProductId(id);
        if(flag > 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean updateProduct(Product product) {

        Integer flag = productDao.updateProduct(product);

        if(flag > 0) {
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<PmsProductComment>  findCommentByProductId(QueryParam queryParam) {

        QueryParam param = new QueryParam();
        param.setPageNum(null);

        JsonModel membersMsg = memberServiceClient.findAllMembers(null ,null);
        List<LinkedHashMap> members = (List<LinkedHashMap>) ((LinkedHashMap)membersMsg.getData()).get("list");

        if(queryParam.getPageNum() == null) {
            PageHelper.startPage(0, 0);
        }else {
            PageHelper.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }

        List<PmsProductComment> comments = productDao.findCommentByProductId(queryParam.getProductId());

        for(PmsProductComment comment : comments ) {

            for(LinkedHashMap member: members) {
                if(member.get("id") == comment.getUser_id()) {
                    comment.setNickname((String) member.get("nickname"));
                    comment.setHeadImg((String) member.get("headImg"));
                    break;
                }
            }
        }

        PageInfo<PmsProductComment> pageInfo = new PageInfo<>(comments);

        return pageInfo;
    }

}
