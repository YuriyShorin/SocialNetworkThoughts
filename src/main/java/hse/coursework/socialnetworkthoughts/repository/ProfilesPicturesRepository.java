package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface ProfilesPicturesRepository {

    @Select("INSERT INTO profiles_pictures(profile_id) " +
            "VALUES ('${profileId}') RETURNING id;")
    Id save(@Param("profileId") UUID profileId);

    @Update("UPDATE profiles_pictures " +
            "SET url = '${path}' " +
            "WHERE id = '${id}';")
    void savePath(@Param("id") UUID id, @Param("path") String path);

    @Results(value = {
            @Result(property = "path", column = "url")
    })
    @Select("SELECT url FROM profiles_pictures " +
            "WHERE profile_id = '${profileId}';")
    FilePath findPathsByProfileId(UUID profileId);
}
