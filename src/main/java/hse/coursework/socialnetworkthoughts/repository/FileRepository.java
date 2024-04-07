package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.URL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FileRepository {

    @Select("INSERT INTO Posts_pictures(post_id, url) " +
            "VALUES ('${postId}', '${url}');")
    void savePicture(UUID postId, String url);

    @Results(value = {
            @Result(property = "url", column = "url")
    })
    @Select("SELECT * FROM Posts_pictures " +
            "WHERE post_id = '${postId}';")
    List<URL> findUrlsByPostId(UUID postId);
}
