package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FeedRepository {

    @Results(value = {
            @Result(property = "postId", column = "post_id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "profileNickname", column = "nickname"),
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
    @Select("SELECT p.id AS post_id, p.theme, p.content, p.likes, p.reposts, p.comments, p.views, " +
            "p.is_repost, p.author_id, p.created_at, p.edited_at, " +
            "pr.id AS profile_id, pr.nickname FROM Subscriptions s " +
            "JOIN Posts p ON s.subscription_profile_id = p.profile_id " +
            "JOIN Profiles pr ON s.subscription_profile_id = pr.id " +
            "WHERE s.profile_id = '${profileId}' " +
            "ORDER BY p.created_at DESC;"
    )
    List<Feed> getFeedByProfileId(UUID profileId);

    @Results(value = {
            @Result(property = "postId", column = "post_id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "profileNickname", column = "nickname"),
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
    @Select("SELECT p.id AS post_id, p.theme, p.content, p.likes, p.reposts, p.comments, p.views, " +
            "p.is_repost, p.author_id, p.created_at, p.edited_at, " +
            "pr.id AS profile_id, pr.nickname FROM Posts p " +
            "JOIN Profiles pr ON p.profile_id = pr.id " +
            "WHERE p.theme ILIKE CONCAT('${theme}', '%') " +
            "ORDER BY p.created_at DESC;"
    )
    List<Feed> getFeedByTheme(String theme);
}
