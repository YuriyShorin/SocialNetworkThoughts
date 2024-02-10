package hse.coursework.socialnetworkthoughts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    private UUID id;

    private UUID userId;

    private String nickname;

    private String status;

    private String description;

    private Integer subscribes;

    private Integer subscribers;

    private List<Post> posts;

    private Timestamp createdAt;
}
