package hse.coursework.socialnetworkthoughts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostResponse {

    private UUID id;

    private UUID profileId;

    private String theme;

    private String content;

    private Long likes;

    private Long reposts;

    private Long comments;

    private Long views;

    private UUID authorId;

    private Timestamp createdAt;

    private Timestamp editedAt;
}
