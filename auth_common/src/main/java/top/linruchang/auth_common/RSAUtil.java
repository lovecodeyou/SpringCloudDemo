package top.linruchang.auth_common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Classname RSAUtil
 * @Description
 * @Date 2020/2/16 22:35
 * @Created by lrc
 */
public class RSAUtil {

    public static void main(String[] args) throws Exception {
//        try {
//            System.out.println(getPublicKey("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        getPublicKey(readFile("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt"));

        RSAUtil.generateKey("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt", "C:\\Users\\lrc\\Desktop\\test\\privateKey.txt", "123456");
        PublicKey publicKey = getPublicKey(readFile("C:\\Users\\lrc\\Desktop\\test\\publicKey.txt"));

        System.out.println(publicKey);

    }
    /**
     * 从文件信息读取转为字节数组byte[]
     * @param fileName
     * @return
     * @throws IOException
     */
    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }



    public static PublicKey getPublicKey(String fileName) throws Exception {
        return getPublicKey(readFile(fileName));
    }



    /**
     * 将公钥的字节数组转为公钥对象
     * @param keyEncoded
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(byte[] keyEncoded) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyEncoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }



    public static PrivateKey getPrivateKey(String fileName) throws Exception {
        return getPrivateKey(readFile(fileName));
    }

    /**
     * 将私钥的字节数组转为私钥对象
     * @param keyEncoded
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(byte[] keyEncoded) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyEncoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }


    /**
     * 生成一个钥匙对在对应的磁盘路径中
     *
     * @param publicKeyPath  公钥路径
     * @param privateKeyPath 私钥路径
     * @param seed           种子
     */
    public static void generateKey(String publicKeyPath, String privateKeyPath, String seed) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());

        //1. 指定密钥对的的大小、以及随机数源
        keyPairGenerator.initialize(1024, secureRandom);

        // 2. 获取密钥对对象
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        // 3. 获取公钥
        PublicKey publicKey = keyPair.getPublic();

        // 4. 获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();

        // 将公钥、私钥分别以一个文件进行进行存储到硬盘上
        writeFile(publicKeyPath, publicKeyEncoded);
        writeFile(privateKeyPath, privateKeyEncoded);

    }

    /**
     * 将密钥持久化到对应的文件中
     * @param keyPath
     * @param keyEncoded
     * @throws Exception
     */
    public static void writeFile(String keyPath, byte[] keyEncoded) throws Exception {

        File file = new File(keyPath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();

        }

        FileOutputStream fos = new FileOutputStream(keyPath);
        fos.write(keyEncoded, 0, keyEncoded.length);
        fos.close();

        //上述三行代码可使用这一行代码代替
        //Files.write(file.toPath(), keyEncoded);
    }


}
