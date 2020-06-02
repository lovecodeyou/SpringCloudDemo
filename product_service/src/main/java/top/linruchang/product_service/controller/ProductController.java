package top.linruchang.product_service.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.linruchang.common.JsonModel;
import top.linruchang.common.StringUtil;
import top.linruchang.product_service.client.MemberServiceClient;
import top.linruchang.product_service.dao.ProductDao;
import top.linruchang.product_service.domain.*;
import top.linruchang.product_service.service.DetailService;
import top.linruchang.product_service.service.ProductService;
import top.linruchang.product_service.service.TypeService;

import javax.servlet.http.HttpServletRequest;
import javax.smartcardio.Card;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.aspectj.apache.bcel.Constants.types;

/**
 * @Classname ProductController
 * @Description
 * @Date 2020/2/5 20:47
 * @Created by lrc
 */

@Controller
@RequestMapping("products")
public class ProductController {


    final  String nginxPrefix = "E:\\IDEA2019\\workspace\\springclouddemo1\\fruit_web\\src\\assets\\images\\";


    @Autowired
    ProductService productService;

    @Autowired
    DetailService detailService;

    @Autowired
    TypeService typeService;

    /**
     * 获取产品表中的所有记录
     * @return
     */

    @Autowired
    MemberServiceClient memberServiceClient;

    @GetMapping("test")
    @ResponseBody
    public JsonModel test() {

        JsonModel jsonModel = new JsonModel();

        jsonModel.setData(memberServiceClient.findByAddressId(13));

        System.out.println(memberServiceClient.findAllMembers(null,null));

        return jsonModel;

    }


    //待改 -- 全部需要改
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Product>  listUI(QueryParam queryParam) {


        if(queryParam == null) {
            queryParam = new QueryParam();
        }
        return productService.findAllProducts(queryParam);
    }

    /**
     * 获取产品详情信息
     * @param id
     * @return
     */
    @GetMapping(value = "{id}")
    @ResponseBody
    public JsonModel findById(@PathVariable("id") Integer id, HttpServletRequest request) {

        System.out.println("======================服务端口号" + request.getServerPort());
        System.out.println("======================本地端口号" + request.getLocalPort());
        System.out.println("======================远程端口号" + request.getRemotePort());


        JsonModel jsonModel = new JsonModel();

        PmsProductDetailinfo detail = detailService.findById(id);

        if(detail != null) {
            jsonModel.setEffective(true);
            jsonModel.setData(detail);
        }else {
            jsonModel.setMessage("获取数据失败：后台可能无此条数据");
        }

        return jsonModel;
    }


    //获取所有产品普通信息 - 带广告图
    @GetMapping("/types")
    @ResponseBody
    public JsonModel findAllType(top.linruchang.domain.QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();

        PageInfo<PmsProductType> pageInfo = typeService.findAll(queryParam);

        List<PmsProductType> types = pageInfo.getList();

        if(types == null && types.size() == 0) {
            jsonModel.setMessage("获取失败：广告以及产品信息");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取数据成功");
        }
        return jsonModel;
    }


    //获取产品类别信息 - 仅类别
    @GetMapping("/onlyTypes")
    @ResponseBody
    public JsonModel findAllOnlyType(top.linruchang.domain.QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();

        PageInfo<PmsProductType> pageInfo = typeService.findAllOnlyType(queryParam);

        List<PmsProductType> types =  pageInfo.getList();

        if(types == null && types.size() == 0) {
            jsonModel.setMessage("获取失败：广告以及产品信息");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取数据成功");
        }
        return jsonModel;
    }


    //获取某个产品分类下的产品普通信息
    @GetMapping("/types/{type_id}")
    @ResponseBody
    public JsonModel findBytypeId(top.linruchang.domain.QueryParam queryParam, @PathVariable("type_id") Integer type_id) {

        if(type_id == null) {
            type_id = 1;
        }

        JsonModel jsonModel = new JsonModel();

        PmsProductType types =  typeService.findById(queryParam,type_id);

        List<Product> products = types.getProducts();

        if(products == null || products.size() == 0) {
            jsonModel.setMessage("获取失败：产品信息可能还没上架");
        }else {
            jsonModel.setData(types);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取数据成功");
        }
        return jsonModel;
    }



    //获取某个产品分类下的产品普通信息
    @GetMapping("/types2/{type_id}")
    @ResponseBody
    public JsonModel findBytypeId2(top.linruchang.domain.QueryParam queryParam, @PathVariable("type_id") Integer type_id) {

        if(type_id == null) {
            type_id = 1;
        }

        JsonModel jsonModel = new JsonModel();
        PageInfo<Product> pageInfo = typeService.findById2(queryParam, type_id);

        List<Product> products = pageInfo.getList();

        if(products == null || products.size() == 0) {
            jsonModel.setMessage("获取失败：产品信息可能还没上架");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取数据成功");
        }
        return jsonModel;
    }



    //查询产品普通信息 - 根据产品ID号
    @GetMapping("/product/{product_id}")
    @ResponseBody
    public Product findByProductId(@PathVariable("product_id")Integer product_id) {


        Product product = productService.findByProductId(product_id);

        return product;

    }


    //删除产品 - 连同与之关联的产品详情表
    @DeleteMapping("{product_id}")
    @ResponseBody
    public JsonModel deleteById(@PathVariable("product_id") Integer product_id) {

        boolean flag = productService.deleteByProductId(product_id);

        JsonModel jsonModel = new JsonModel();
        if(flag) {

            jsonModel.setEffective(true);
            jsonModel.setMessage("删除ID为" + product_id +"产品成功");

        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("删除ID为" + product_id +"产品失败");
        }
        return jsonModel;

    }

    //修改某个产品普通信息
    @PutMapping("")
    @ResponseBody
    public JsonModel updateProduct(@RequestBody Product product) {

        boolean flag = productService.updateProduct(product);

        JsonModel jsonModel = new JsonModel();

        Integer product_id = product.getId();
        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("修改ID为" + product_id +"产品成功");

        }else {
            jsonModel.setEffective(true);
            jsonModel.setMessage("修改ID为" + product_id +"产品失败");
        }
        return jsonModel;

    }

    //上传普通产品的简介图片
    @PostMapping("img")
    @ResponseBody
    public JsonModel productImgUpload(@RequestParam("file") MultipartFile mf, @RequestParam("imgURI") String imgURI,  @RequestParam("productId")Integer productId) {

        Product product = new Product();

        product.setId(productId);
        product.setImg_url(imgURI + mf.getOriginalFilename());

        System.out.println("imgURI: " + imgURI);

        JsonModel jsonModel = new JsonModel();

        System.out.println(mf.getSize());
        System.out.println(mf.getName());
        System.out.println(mf.getOriginalFilename());
        System.out.println(mf.getContentType());

        File file = null;
        if(StringUtil.isEmpty(imgURI)) {
            file = new File(nginxPrefix + mf.getOriginalFilename());
        }else {
            file = new File(nginxPrefix + imgURI + mf.getOriginalFilename());
        }

        jsonModel.setEffective(false);
        jsonModel.setMessage("上传图片失败");

        boolean flag = false;
        try {
            mf.transferTo(file);
            flag = productService.updateProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
            return jsonModel;
        }

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("上传图片成功");
        }

        return jsonModel;

    }


    //修改产品的详情信息
    @PutMapping("{productId}")
    @ResponseBody
    public JsonModel updateProductDetail(@PathVariable("productId")Integer id, @RequestBody PmsProductDetailinfo pmsProductDetailinfo) {

        JsonModel jsonModel = new JsonModel();

        pmsProductDetailinfo.setId(id);

        Boolean flag = detailService.updateProductDetail(pmsProductDetailinfo);

        if(flag) {
            jsonModel.setMessage("修改成功ID是" + pmsProductDetailinfo.getId() + "的产品详情信息");
            jsonModel.setEffective(true);
        }else {
            jsonModel.setMessage("修改失败ID是" + pmsProductDetailinfo.getId() + "的产品详情信息");
            jsonModel.setEffective(false);
        }

        return jsonModel;

    }

    //获取产品评论
    @GetMapping(value="/comments/{productId}")
    @ResponseBody
    public JsonModel findProductComments(@PathVariable("productId") Integer productId, QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();
        if(queryParam.getProductId() != productId) {
            jsonModel.setMessage("请不要擅自修改参数");
        }else {
            PageInfo<PmsProductComment> pageInfo = productService.findCommentByProductId(queryParam);
            if(pageInfo.getList() == null || pageInfo.getList().size() == 0) {
                jsonModel.setMessage("我们是很有底线的！！！");
            }else {
                jsonModel.setData(pageInfo);
                jsonModel.setEffective(true);
                jsonModel.setMessage("获取数据成功");
            }
        }

        return jsonModel;
    }

}
