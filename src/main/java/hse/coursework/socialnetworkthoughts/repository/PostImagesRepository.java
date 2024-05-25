package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface PostImagesRepository {

    @Select("INSERT INTO Posts_images(post_id) " +
            "VALUES ('${postId}') RETURNING id;")
    Id save(@Param("postId") UUID postId);

    @Update("UPDATE Posts_images " +
            "SET url = '${path}' " +
            "WHERE id = '${id}';")
    void savePath(@Param("id") UUID id, @Param("path") String path);

    @Results(value = {
            @Result(property = "path", column = "url")
    })
    @Select("SELECT url FROM Posts_images " +
            "WHERE post_id = '${postId}';")
    List<ImagePath> findPathsByPostId(@Param("postId") UUID postId);

    @Delete("DELETE FROM Posts_images " +
            "WHERE post_id = '${postId}';")
    void deleteByPostId(@Param("postId") UUID postId);
}
