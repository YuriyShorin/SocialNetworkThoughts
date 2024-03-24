package hse.coursework.socialnetworkthoughts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Id response dto")
@Data
@AllArgsConstructor
public class IdResponse {

    @Schema(description = "id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;
}
