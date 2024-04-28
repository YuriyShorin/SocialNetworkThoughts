package hse.coursework.socialnetworkthoughts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private UUID id;

    private UUID profileId;

    private UUID postId;

    private String content;

    private Long likes;

    private Timestamp createdAt;

    private Timestamp editedAt;
}
