package hse.coursework.socialnetworkthoughts.security.mapper;

import hse.coursework.socialnetworkthoughts.security.model.Id;
import hse.coursework.socialnetworkthoughts.security.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Users (username, password) " +
            "VALUES ('${username}', '${password}') RETURNING id;")
    Id save(User user);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role")
    })
    @Select("SELECT * FROM Users " +
            "WHERE username = '${username}';")
    Optional<User> findByUsername(String username);
}
