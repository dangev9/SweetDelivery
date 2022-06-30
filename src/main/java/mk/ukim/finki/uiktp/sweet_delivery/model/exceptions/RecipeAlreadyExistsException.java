package mk.ukim.finki.uiktp.sweet_delivery.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecipeAlreadyExistsException extends RuntimeException {
    public RecipeAlreadyExistsException(String name) {
        super("Recipe with the name \"" + name + "\" already exists!");
    }
}
