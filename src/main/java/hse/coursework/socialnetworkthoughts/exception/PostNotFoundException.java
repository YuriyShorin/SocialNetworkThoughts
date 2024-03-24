package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Post is not found")
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super();
    }
}
