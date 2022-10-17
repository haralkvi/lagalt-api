package no.noroff.lagalt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IdAlreadyExistsException extends RuntimeException {

    public IdAlreadyExistsException(int id) {
        super("ID: " + id + " is already present in database.");
    }

    public IdAlreadyExistsException(String id) {
        super("ID: " + id + " is already present in database.");
    }
}
