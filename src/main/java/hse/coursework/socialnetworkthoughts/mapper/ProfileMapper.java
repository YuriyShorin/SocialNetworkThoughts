package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProfileMapper {

    @Insert("INSERT INTO Profiles (user_id, nickname) " +
            "VALUES ('${userId}', '${nickname}');")
    void save(UUID userId, String nickname);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "subscribes", column = "subscribes"),
            @Result(property = "subscribers", column = "subscribers"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "posts", column = "id", javaType = List.class, many = @Many(select = "findPostsByProfileId")),
    })
    @Select("SELECT * FROM Profiles pr " +
            "WHERE pr.user_id = '${userId}';")
    Optional<Profile> findByUserId(@Param("userId") UUID userId);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "theme", column = "theme"),
            @Result(property = "content", column = "content"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "reposts", column = "reposts"),
            @Result(property = "comments", column = "comments"),
            @Result(property = "views", column = "views"),
            @Result(property = "isRepost", column = "is_repost"),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "editedAt", column = "edited_at"),
    })
    @Select("SELECT * FROM Posts p " +
            "WHERE p.profile_id = '${profileId}';")
    List<Post> findPostsByProfileId(@Param("profileId") UUID profileId);


    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "subscribes", column = "subscribes"),
            @Result(property = "subscribers", column = "subscribers")

    })
    @Select("SELECT * FROM Profiles " +
            "WHERE id = '${id}';")
    Optional<Profile> findById(UUID id);
}
