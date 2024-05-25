package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Profile;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProfileRepository {

    @Insert("INSERT INTO Profiles (user_id, nickname) " +
            "VALUES ('${userId}', '${nickname}');")
    void save(UUID userId, String nickname);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "status", column = "status"),
            @Result(property = "subscribes", column = "subscribes"),
            @Result(property = "subscribers", column = "subscribers"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "posts", column = "id", javaType = List.class,
                    many = @Many(select = "hse.coursework.socialnetworkthoughts.repository.PostRepository.findAllByProfileId")),
    })
    @Select("SELECT * FROM Profiles " +
            "WHERE user_id = '${userId}';")
    Optional<Profile> findByUserId(@Param("userId") UUID userId);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "status", column = "status"),
            @Result(property = "subscribes", column = "subscribes"),
            @Result(property = "subscribers", column = "subscribers"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "posts", column = "id", javaType = List.class,
                    many = @Many(select = "hse.coursework.socialnetworkthoughts.repository.PostRepository.findAllByProfileId")),
    })
    @Select("SELECT * FROM Profiles " +
            "WHERE id = '${profileId}';")
    Optional<Profile> findById(UUID profileId);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "status", column = "status"),
            @Result(property = "subscribes", column = "subscribes"),
            @Result(property = "subscribers", column = "subscribers")

    })
    @Select("SELECT * FROM Profiles " +
            "WHERE nickname ILIKE CONCAT('${nickname}', '%') " +
            "AND id != '${id}' " +
            "ORDER BY nickname;")
    List<Profile> findByNickname(String nickname, UUID id);

    @Update("UPDATE Profiles " +
            "SET nickname =  '${nickname}', status = '${status}', description = '${description}', " +
            "subscribes = '${subscribes}', subscribers = '${subscribers}' " +
            "WHERE id = '${id}';")
    void update(Profile profile);
}
