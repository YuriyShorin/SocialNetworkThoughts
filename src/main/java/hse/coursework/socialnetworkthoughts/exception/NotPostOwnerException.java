package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Вы не являетесь владельцем этого поста")
public class NotPostOwnerException extends RuntimeException {

    public NotPostOwnerException() {
        super();
    }
}
