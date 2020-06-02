package top.linruchang.common;

import java.util.Random;

/**
 * @Classname RandomUtil
 * @Description
 * @Date 2020/5/4 22:44
 * @Created by lrc
 */
public class RandomUtil {

    public static Random random = new Random(99999);

    public static String getVerificationCode() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }



}
