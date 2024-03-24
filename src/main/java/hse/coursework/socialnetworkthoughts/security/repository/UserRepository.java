package hse.coursework.socialnetworkthoughts.security.repository;

import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.security.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserRepository {

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Users (username, password, role) " +
            "VALUES ('${username}', '${password}', '${role}') RETURNING id;")
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
