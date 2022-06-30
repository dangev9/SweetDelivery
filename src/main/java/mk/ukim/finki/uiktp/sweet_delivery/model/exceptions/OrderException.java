package mk.ukim.finki.uiktp.sweet_delivery.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderException extends RuntimeException{
    public OrderException() {
        super("Order Exception");
    }
}
