package top.linruchang.auth_common;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname JWTUtil
 * @Description
 * @Date 2020/2/16 23:26
 * @Created by lrc
 */
public class JWTUtil {

    public static void main(String[] args) throws Exception {

        // 生成RSA公钥、私钥并保存到桌面上
        RSAUtil.generateKey("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt", "C:\\Users\\lrc\\Desktop\\test\\privateKey.txt", "sdfds");

        // 将桌面放的公私钥文件转成 对象
        PublicKey publicKey = RSAUtil.getPublicKey("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt");
        PrivateKey privateKey = RSAUtil.getPrivateKey("C:\\Users\\lrc\\Desktop\\test\\privateKey.txt");

        // 生成Token字符串（信息：info:hello）

        Map messgs = new HashMap();
        messgs.put("loginName", "臭小子");
        messgs.put("userId", 10);

        String token = generateToken(messgs, privateKey, 1);

        // 查看Token字符串
        System.out.println(token);

        // 解析Token字符串，获取 info字段对应的信息
        System.out.println(getInfoFromToken(token,"loginName", publicKey));
        System.out.println(getInfoFromToken(token,"userId", publicKey));

    }



    // 1. 产生一个Token - 使用服务器的私钥进行签名 - 公钥私钥都不会对外发布
    //Token负载存的是用户信息、Token过期时间为1天，1天后需要重新登录
    public static String generateToken(Map<String, Object> keysValues, PrivateKey privateKey, int expire) {

        JwtBuilder builder = Jwts.builder();
        // Token负载部分的内容
//        builder.claim("info", oriInfo);
        builder.setClaims(keysValues);

        //设置Token的有效时间
        builder.setExpiration(DateTime.now().plusDays(expire).toDate());

        //Token的签名部分 - 使用RSA私钥进行签名
        builder.signWith(SignatureAlgorithm.RS256, privateKey);
        return builder.compact();
    }

    //解析Token的负载内容，如果解析不出来，会发生异常 - 网关需要解析Token
    public static String getInfoFromToken(String token, String key, PublicKey publicKey) {

        // 验证Token，使用公钥解出签名内容，比较签名内容与Token是否一致，并且将Token转成一个解析对象
        //如果解析不出来，向外抛出异常
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);

        // 获取Token的负载部分的对象 - 其实就是实现了自定义的Map接口
        Claims body = claimsJws.getBody();

        //获取Key的值
        return body.get(key).toString();

    }

}
