package top.linruchang.auth_service.service;

import top.linruchang.auth_service.domain.Auth;
import top.linruchang.auth_service.domain.AuthMessage;

/**
 * @Classname AuthService
 * @Description
 * @Date 2020/2/17 0:30
 * @Created by lrc
 */
public interface AuthService {

    public AuthMessage auth(Auth auth);

    String info(String token, String key);

    Integer sendEmail(String receiverEmail);

}
