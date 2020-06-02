package top.linruchang.product_service.util;

/**
 * @Classname StringUtil
 * @Description
 * @Date 2020/2/6 22:47
 * @Created by lrc
 */
public class StringUtil {

    public static boolean isEmpty(String source) {

        if(source == null || source.trim()=="") {
            return true;
        }
        return false;
    }

}
