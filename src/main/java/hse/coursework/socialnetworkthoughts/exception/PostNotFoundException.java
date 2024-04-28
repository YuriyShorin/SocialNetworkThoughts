package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Пост не найден")
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super();
    }
}
