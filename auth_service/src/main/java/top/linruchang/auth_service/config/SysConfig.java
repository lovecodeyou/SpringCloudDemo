package top.linruchang.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;
import top.linruchang.auth_common.RSAUtil;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Classname SysConfig
 * @Description
 * @Date 2020/2/17 0:53
 * @Created by lrc
 */

@Repository
@ConfigurationProperties(prefix = "top.linruchang")
public class SysConfig {

    int expire;
    String publicKeyPath;
    String privateKeyPath;
    String seed;

    @Value("${spring.mail.username}")
    String senderEmail;


    PublicKey publicKey;
    PrivateKey privateKey;

    public String getSenderEmailAddress() {
        return senderEmail;
    }


    @PostConstruct
    public void createKeyPair() {
        try {
            RSAUtil.generateKey(publicKeyPath, privateKeyPath, seed);

            setPublicKey(RSAUtil.getPublicKey(publicKeyPath));

            setPrivateKey(RSAUtil.getPrivateKey(privateKeyPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "expire=" + expire +
                ", publicKeyPath='" + publicKeyPath + '\'' +
                ", privateKeyPath='" + privateKeyPath + '\'' +
                ", seed='" + seed + '\'' +
                ", publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                '}';
    }
}
