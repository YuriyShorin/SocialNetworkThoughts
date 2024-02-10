package hse.coursework.socialnetworkthoughts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {

    private String theme;

    private String content;
}
