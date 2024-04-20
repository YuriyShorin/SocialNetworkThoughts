package hse.coursework.socialnetworkthoughts.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Post {

    private UUID id;

    private UUID profileId;

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
