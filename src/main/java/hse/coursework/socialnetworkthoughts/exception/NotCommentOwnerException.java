package hse.coursework.socialnetworkthoughts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Вы не являетесь владельцем комментария или комментарий не найден")
public class NotCommentOwnerException extends RuntimeException {

    public NotCommentOwnerException() {
        super();
    }
}