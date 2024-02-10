package hse.coursework.socialnetworkthoughts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Profile not found")
public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super();
    }
}
