package top.linruchang.product_service.domain;

import top.linruchang.common.StringUtil;

/**
 * @Classname PmsProductDetailinfo
 * @Description
 * @Date 2020/3/12 21:41
 * @Created by lrc
 */
public class PmsProductDetailinfo {

//     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '某个品牌下的产品id',
//            `description` varchar(255) NOT NULL COMMENT '产品描述信息',
//            `slide_show_imgs` varchar(500) NOT NULL COMMENT '轮播图图片地址，地址逗号分隔',
//            `graphic_details_imgs` varchar(500) NOT NULL COMMENT '图文详情图片地址，地址逗


    Integer id;
    String description;
    String[] slide_show_imgs;
    String[] graphic_details_imgs;
    Integer freight;

    String brand_name;

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getSlide_show_imgs() {
        return slide_show_imgs;
    }

    public void setSlide_show_imgs(String slide_show_imgs) {
        if(!StringUtil.isEmpty((slide_show_imgs))) {
            this.slide_show_imgs = slide_show_imgs.split(",");
            return;
        }
        this.slide_show_imgs = null;
    }

    public String[] getGraphic_details_imgs() {
        return graphic_details_imgs;
    }

    public void setGraphic_details_imgs(String graphic_details_imgs) {
        if(!StringUtil.isEmpty((graphic_details_imgs))) {
            this.graphic_details_imgs = graphic_details_imgs.split(",");
            return;
        }
        this.graphic_details_imgs = null;
    }
}
