package top.linruchang.api_gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.linruchang.auth_common.RSAUtil;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Classname MyConfig
 * @Description
 * @Date 2020/2/18 9:25
 * @Created by lrc
 */

@ConfigurationProperties(prefix = "top.linruchang")
public class MyConfig {

    private String publicKeyLocation;
    private String privateKeyLocation;
    private String seed;




    private PublicKey publicKey;


    private PrivateKey privateKey;



    private String excludeURI;

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }


    public void setPublicKeyLocation(String publicKeyLocation) {
        this.publicKeyLocation = publicKeyLocation;
    }

    public void setPrivateKeyLocation(String privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {


        this.privateKey = privateKey;
    }

    public String getExcludeURI() {
        return excludeURI;
    }

    public void setExcludeURI(String excludeURI) {
        this.excludeURI = excludeURI;
    }


    public String getPublicKeyLocation() {
        return publicKeyLocation;
    }

    public String getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    @Bean
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Bean
    public PrivateKey getPrivateKey() {
        return privateKey;
    }


    @PostConstruct
    public void createKeys() {
        System.out.println(publicKeyLocation);
        try {
            publicKey = RSAUtil.getPublicKey(publicKeyLocation);
            privateKey = RSAUtil.getPrivateKey(privateKeyLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // 跨域设置
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // 允许cookies跨域 - 用户凭证
        config.setAllowCredentials(true);

        // #允许跨域访问的域名地址+端口号，*表示所有域名+端口号被允许，在SpringMVC中，
        // 如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
//        config.addAllowedOrigin("http://localhost:8888");


        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");

        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(7200L);

        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("C:\\Users\\lrc\\Desktop\\测试\\临时路径");
        return factory.createMultipartConfig();
    }

}
