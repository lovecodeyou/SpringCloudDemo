package top.linruchang.product_service.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 * @Classname MyConfig
 * @Description
 * @Date 2020/3/28 23:25
 * @Created by lrc
 */

@SpringBootConfiguration
public class MyConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("C:\\Users\\lrc\\Desktop\\测试\\临时路径");
        return factory.createMultipartConfig();
    }

}
