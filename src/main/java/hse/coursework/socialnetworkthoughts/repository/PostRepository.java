package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Like;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface PostRepository {

    @Select("INSERT INTO Posts (profile_id, theme, content, author_id) " +
            "VALUES ('${profileId}', '${theme}', '${content}', '${authorId}') RETURNING id;")
    Id save(Post post);

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
    @Select("SELECT * FROM Posts " +
            "WHERE id = '${id}';")
    Optional<Post> findById(UUID id);

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
    @Select("SELECT * FROM Posts " +
            "WHERE id = '${id}' AND profile_id = '${profileId}';")
    Optional<Post> findByIdAndProfileId(UUID id, UUID profileId);

    @Update("UPDATE Posts " +
            "SET theme = '${theme}', content = '${content}' " +
            "WHERE id = '${id}';")
    void update(Post post);

    @Delete("DELETE FROM Posts " +
            "WHERE id = '${id}';")
    void delete(UUID id);

    @SuppressWarnings("unused")
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
    @Select("SELECT * FROM Posts " +
            "WHERE profile_id = '${profileId}' " +
            "ORDER BY created_at DESC;")
    List<Post> findAllByProfileId(@Param("profileId") UUID profileId);

    @Results(value = {
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "postId", column = "post_id")
    })
    @Select("SELECT profile_id, post_id FROM Likes " +
            "WHERE profile_id = '${profileId}' " +
            "AND post_id = '${postId}';")
    Optional<Like> findLike(@Param("profileId") UUID profileId, @Param("postId") UUID postId);

    @Insert("INSERT INTO Likes(profile_id, post_id) " +
            "VALUES ('${profileId}', '${postId}');")
    void like(@Param("profileId") UUID profileId, @Param("postId") UUID postId);

    @Delete("DELETE FROM Likes " +
            "WHERE profile_id = '${profileId}' " +
            "AND post_id = '${postId}';")
    void unlike(@Param("profileId") UUID profileId, @Param("postId") UUID postId);
}