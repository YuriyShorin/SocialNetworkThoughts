package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.View;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ViewRepository {

    @Results(value = {
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "postId", column = "post_id")
    })
    @Select("SELECT profile_id, post_id FROM Views " +
            "WHERE profile_id = '${profileId}' " +
            "AND post_id = '${postId}';")
    Optional<View> findByProfileIdAndPostId(@Param("profileId") UUID profileId, @Param("postId") UUID postId);

    @Insert("INSERT INTO Views(profile_id, post_id) " +
            "VALUES ('${profileId}', '${postId}');")
    void save(@Param("profileId") UUID profileId, @Param("postId") UUID postId);
}
