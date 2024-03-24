package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Post is not yours")
public class NotPostOwnerException extends RuntimeException {

    public NotPostOwnerException() {
        super();
    }
}
