package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FileRepository {

    @Select("INSERT INTO posts_files(post_id) " +
            "VALUES ('${postId}') RETURNING id;")
    Id save(@Param("postId") UUID postId);

    @Update("UPDATE posts_files " +
            "SET url = '${path}' " +
            "WHERE id = '${id}';")
    void savePath(@Param("id") UUID id, @Param("path") String path);

    @Results(value = {
            @Result(property = "path", column = "url")
    })
    @Select("SELECT url FROM posts_files " +
            "WHERE post_id = '${postId}';")
    List<FilePath> findPathsByPostId(@Param("postId") UUID postId);
}
