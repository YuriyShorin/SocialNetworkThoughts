package hse.coursework.socialnetworkthoughts.exception.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class ErrorResponseDto {

    private String errorMessage;

    private int statusCode;

    private OffsetDateTime time;
}
