package no.noroff.lagalt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(int id) {
        super("Comment does not exist with ID: " + id);
    }
}
