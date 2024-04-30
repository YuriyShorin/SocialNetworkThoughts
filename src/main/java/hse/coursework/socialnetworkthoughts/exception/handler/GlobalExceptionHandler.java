package hse.coursework.socialnetworkthoughts.exception.handler;

import hse.coursework.socialnetworkthoughts.enums.SeparatorEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonRuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CommonRuntimeException exception) {
        String exceptionMessage = exception.getMessage();

        if (exceptionMessage.contains(SeparatorEnum.COLON.getValue())) {
            String[] exceptionMessages = exceptionMessage.split(SeparatorEnum.COLON.getValue());

            ErrorResponseDto errorResponse = new ErrorResponseDto()
                    .setErrorMessage(exceptionMessages[1])
                    .setStatusCode(Integer.parseInt(exceptionMessages[0]))
                    .setTime(OffsetDateTime.now());

            return ResponseEntity
                    .status(HttpStatus.valueOf(Integer.parseInt(exceptionMessages[0])))
                    .body(errorResponse);
        }

        ErrorResponseDto errorResponse = new ErrorResponseDto()
                .setErrorMessage(exception.getMessage())
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setTime(OffsetDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponse);
    }
}