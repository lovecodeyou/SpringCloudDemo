package top.linruchang.product_service.domain;

import java.util.List;

/**
 * @Classname AdvAndProducts
 * @Description
 * @Date 2020/3/11 22:00
 * @Created by lrc
 */
public class PmsProductType {

    String id;
    String name;

    //类别广告图
    String img;
    List<Product> products = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "PmsProductType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", products=" + products +
                '}';
    }
}
