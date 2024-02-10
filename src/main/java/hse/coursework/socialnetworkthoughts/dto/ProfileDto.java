package hse.coursework.socialnetworkthoughts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProfileDto {

    private UUID id;

    private String nickname;

    private String status;

    private String description;

    private int subscribes;

    private int subscribers;

    List<PostResponse> postDtos;
}
