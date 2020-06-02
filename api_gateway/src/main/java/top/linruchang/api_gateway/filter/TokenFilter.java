package top.linruchang.api_gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import top.linruchang.api_gateway.config.MyConfig;
import top.linruchang.auth_common.JWTUtil;
import top.linruchang.auth_common.RSAUtil;
import top.linruchang.common.JsonModel;
import top.linruchang.common.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

import static top.linruchang.common.StringUtil.*;

/**
 * @Classname loginFilter
 * @Description
 * @Date 2020/2/8 14:52
 * @Created by lrc
 */

@Component
public class TokenFilter extends ZuulFilter {

    final String INFO_KEY = "userId";
    @Autowired
    MyConfig myConfig;

    //定义网关过滤的类型
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //定义网关过滤器的优先顺序，越小有优先过滤
    @Override
    public int filterOrder() {
        return 6;
    }

    //判断是否应该放行请求 true调用run方法进行逻辑判断决定是否放行，false不调用run方法直接放行处理
    @Override
    public boolean shouldFilter() {


        String[] excludeURI = myConfig.getExcludeURI().split(",");

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();


        //String token = request.getHeader("token");

        //1. 与用户信息无关的请求 - 直接放行某些请求

        String requestURI = request.getRequestURI();
        for (String uri : excludeURI) {
            //1.1 如果请求的数据与用户信息无关，则直接放行请求
            if (requestURI.startsWith(uri)) {
                return false;
            }

        }

        //1.2 否认该请求继续进行进一步过璐，即Run函数
        return true;
    }


    // 核心方法，未放行之后的逻辑处理
    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 1. 获取请求头中的Token
        String token = request.getHeader("token");

        PublicKey publicKey = null;

        //2. 如果未携带Token，直接响应会一个未授权的信息
        if (StringUtil.isEmpty(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

            JsonModel jsonModel = new JsonModel();
            jsonModel.setEffective(false);
            jsonModel.setMessage("Token缺失，请进行账号登录");

            String json = JSON.toJSONString(jsonModel);
            requestContext.setResponseBody(json);
            return null;
        } else {

            //3. 如果该Token没有将公钥解密出用户ID号，直接响应会一个未授权的信息
            publicKey = myConfig.getPublicKey();

            String info = null;
            try {

                info = JWTUtil.getInfoFromToken(token, INFO_KEY, publicKey);
            } catch (Exception e) {
                requestContext.setSendZuulResponse(false);
                requestContext.getResponse().setContentType("application/json;charset=UTF-8");
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                JsonModel jsonModel = new JsonModel();
                jsonModel.setEffective(false);
                jsonModel.setMessage("无效Token，请进行账号登录");
                String json = JSON.toJSONString(jsonModel);
                requestContext.setResponseBody(json);

                return null;
            }

            //4. 如果用户ID为空，说明前端有人在仿造Token，直接响应一个未授权的信息
            if (StringUtil.isEmpty(info)) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                JsonModel jsonModel = new JsonModel();
                jsonModel.setEffective(false);
                jsonModel.setMessage("无效Token，请进行账号登录");
                String json = JSON.toJSONString(jsonModel);
                requestContext.setResponseBody(json);
                return null;
            }
        }

        //5、上面执行的代码都通过，则直接放行请求 - 即Token有信息且没有被前端修改过
        return null;
    }
}
