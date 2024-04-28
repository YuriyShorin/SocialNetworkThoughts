package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Пост не лайкнут")
public class PostNotLikedException extends RuntimeException {

    public PostNotLikedException() {
        super();
    }
}

