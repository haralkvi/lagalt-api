package no.noroff.lagalt.models;

import lombok.Data;

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