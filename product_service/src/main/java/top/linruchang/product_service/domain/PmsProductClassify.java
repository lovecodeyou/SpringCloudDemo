package top.linruchang.product_service.domain;

/**
 * @Classname PmsProductClassify
 * @Description
 * @Date 2020/3/10 22:24
 * @Created by lrc
 */
public class PmsProductClassify {


    Integer id;
    String uri;
    String src;
    String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PmsProductClassify{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", src='" + src + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
