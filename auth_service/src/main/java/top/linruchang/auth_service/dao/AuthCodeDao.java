package top.linruchang.auth_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.domain.UmsUserAuthcode;

/**
 * @Classname AuthCodeDao
 * @Description
 * @Date 2020/5/5 2:17
 * @Created by lrc
 */

@Mapper
public interface AuthCodeDao {

    Integer addAuthCode(UmsUserAuthcode umsUserAuthcode);

}
