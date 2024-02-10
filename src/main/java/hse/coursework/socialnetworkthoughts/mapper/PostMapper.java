package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.model.Post;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO Posts (profile_id, theme, content, author_id) " +
            "VALUES ('${profileId}', '${theme}', '${content}', '${authorId}');")
    void save(Post post);
}
