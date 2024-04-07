package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Like;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface LikeRepository {

    @Results(value = {
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "postId", column = "post_id")
    })
    @Select("SELECT profile_id, post_id FROM Likes " +
            "WHERE profile_id = '${profileId}' " +
            "AND post_id = '${postId}';")
    Optional<Like> findByProfileId(@Param("profileId") UUID profileId, @Param("postId") UUID postId);

    @Insert("INSERT INTO Likes(profile_id, post_id) " +
            "VALUES ('${profileId}', '${postId}');")
    void save(@Param("profileId") UUID profileId, @Param("postId") UUID postId);

    @Delete("DELETE FROM Likes " +
            "WHERE profile_id = '${profileId}' " +
            "AND post_id = '${postId}';")
    void delete(@Param("profileId") UUID profileId, @Param("postId") UUID postId);
}
