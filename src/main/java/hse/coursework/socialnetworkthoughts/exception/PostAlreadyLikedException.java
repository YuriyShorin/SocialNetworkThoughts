package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Пост уже лайкнут")
public class PostAlreadyLikedException extends RuntimeException {

    public PostAlreadyLikedException() {
        super();
    }
}
