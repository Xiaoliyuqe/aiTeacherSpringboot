package com.example.myaiteacher.mapper;

import com.example.myaiteacher.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from t_users")
    List<User> selectAllUsers();

//    @Select("select * from t_users " +
//            "where username like concat('%',#{username},'%') and nickname like concat('%',#{nickname},'%') " +
//            "limit #{startIndex}, #{pageSize}")
//    List<User> selectByCondition(String username, String nickname, Integer startIndex, Integer pageSize);

//    @Select("select count(*) from t_users " +
//            "where username like concat('%',#{username},'%') and nickname like concat('%',#{nickname},'%')")
//    Integer total_selectBtCondition(String username, String nickname);

    @Insert("INSERT INTO t_users (username, password, phoneNum, email) VALUES (#{username}, #{password}, #{phoneNum}, #{email})")
        //@Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addUser(String username, String password, String phoneNum, String email);

    @Select("select username from t_users where username = #{username}")
    List<String> selectUsernameByUsername(String username);

    @Select("select * from t_users where username = #{username}")
    User selectUserByUsername(String username);

    @Update("update t_users set password=#{password} where username=#{username}")
    Integer changePassword(String username, String password);

    @Delete("delete from t_users where username = #{username}")
    Integer delete(String username);
}
