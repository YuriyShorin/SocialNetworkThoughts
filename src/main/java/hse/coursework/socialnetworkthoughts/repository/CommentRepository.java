package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Comment;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CommentRepository {

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Comments (profile_id, post_id, content) " +
            "VALUES ('${profileId}', '${postId}', '${content}') RETURNING id;")
    Id save(Comment comment);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "content", column = "content"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "editedAt", column = "edited_at"),
    })
    @Select("SELECT * FROM Comments " +
            "WHERE id = '${id}' " +
            "AND profile_id = '${profileId}';")
    Optional<Comment> findByIdAndProfileId(UUID id, UUID profileId);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "content", column = "content"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "editedAt", column = "edited_at"),
    })
    @Select("SELECT * FROM Comments " +
            "WHERE post_id = '${postId}';")
    List<Comment> findByPostId(UUID postId);

    @Update("UPDATE Comments " +
            "SET content = '${content}' " +
            "WHERE id = '${id}';")
    void update(Comment comment);

    @Delete("DELETE FROM Comments " +
            "WHERE id = '${id}';")
    void deleteById(UUID id);
}
