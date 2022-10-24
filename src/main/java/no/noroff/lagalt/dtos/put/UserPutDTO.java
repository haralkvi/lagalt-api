package no.noroff.lagalt.dtos.put;

import lombok.Data;
import no.noroff.lagalt.models.User;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserPutDTO implements Serializable {
    private final String id;
    private final String name;
    private final String email;
}