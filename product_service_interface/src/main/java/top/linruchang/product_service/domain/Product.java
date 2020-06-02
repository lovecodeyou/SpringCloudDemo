package top.linruchang.product_service.domain;

/**
 * @Classname Product
 * @Description
 * @Date 2020/2/5 20:49
 * @Created by lrc
 */
public class Product {



    Integer id;
    Integer type_id;
    Integer brand_id;
    String name;
    String specifications;
    Integer current_price;
    Integer history_price;
    String special_information;
    String img_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Integer getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Integer current_price) {

        this.current_price = current_price;
    }

    public Integer getHistory_price() {
        return history_price;
    }

    public void setHistory_price(Integer history_price) {
        this.history_price = history_price;
    }

    public String getSpecial_information() {
        return special_information;
    }

    public void setSpecial_information(String special_information) {
        this.special_information = special_information;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", brand_id=" + brand_id +
                ", name='" + name + '\'' +
                ", specifications='" + specifications + '\'' +
                ", current_price=" + current_price +
                ", history_price=" + history_price +
                ", special_information='" + special_information + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
