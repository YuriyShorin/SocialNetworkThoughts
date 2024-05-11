package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Id;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
