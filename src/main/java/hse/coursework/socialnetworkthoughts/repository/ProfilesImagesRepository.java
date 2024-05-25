package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface ProfilesImagesRepository {

    @Select("INSERT INTO Profiles_images(profile_id) " +
            "VALUES ('${profileId}') RETURNING id;")
    Id save(@Param("profileId") UUID profileId);

    @Update("UPDATE Profiles_images " +
            "SET url = '${path}' " +
            "WHERE id = '${id}';")
    void savePath(@Param("id") UUID id, @Param("path") String path);

    @Results(value = {
            @Result(property = "path", column = "url")
    })
    @Select("SELECT url FROM Profiles_images " +
            "WHERE profile_id = '${profileId}';")
    ImagePath findPathsByProfileId(UUID profileId);
}
