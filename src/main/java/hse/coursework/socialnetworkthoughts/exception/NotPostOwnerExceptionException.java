package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Комментарий не найден")
public class NotPostOwnerExceptionException extends RuntimeException {

    public NotPostOwnerExceptionException() {
        super();
    }
}