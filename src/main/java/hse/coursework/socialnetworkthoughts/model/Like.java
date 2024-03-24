package hse.coursework.socialnetworkthoughts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    private UUID profileId;

    private UUID postId;
}
