package top.linruchang.member_service.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * @Classname MyConfig
 * @Description
 * @Date 2020/3/6 16:12
 * @Created by lrc
 */
@Configuration
@EnableSwagger2
public class MyConfig {


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public IRule MyIRule() {
        return new RoundRobinRule() ;
    }


    @Bean
    public Docket getDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.linruchang.member_service.controller"))
                .build();
    }

    public ApiInfo getApiInfo() {

        Contact myConcat = new Contact("lrc", "", "2329073340@qq.com");

        return new ApiInfo("用户服务接口文档", "针对用户信息的操作", "1.0", "http://linruchang.top/", myConcat, "", "", new ArrayList());

    }

    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }


    //跨域
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 2允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 3允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        // 4
        corsConfiguration.setAllowCredentials(true);

        return corsConfiguration;
    }

}
