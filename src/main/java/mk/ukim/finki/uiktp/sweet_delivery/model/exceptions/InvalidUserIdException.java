package mk.ukim.finki.uiktp.sweet_delivery.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(Long userId) {
        super("The user with the id: " + userId + ", has not been found");
    }
}
