package hse.coursework.socialnetworkthoughts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feed {

    private UUID postId;

    private UUID profileId;

    private String profileNickname;

    private String theme;

    private String content;

    private Long likes;

    private Long reposts;

    private Long comments;

    private Long views;

    private Boolean isRepost;

    private UUID authorId;

    private Timestamp createdAt;

    private Timestamp editedAt;
}
