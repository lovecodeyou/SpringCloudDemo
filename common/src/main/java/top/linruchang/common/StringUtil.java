package top.linruchang.common;

/**
 * @Classname StringUtil
 * @Description
 * @Date 2020/3/6 23:19
 * @Created by lrc
 */
public class StringUtil {

    public static boolean isEmpty(String str) {

        if(str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

}
