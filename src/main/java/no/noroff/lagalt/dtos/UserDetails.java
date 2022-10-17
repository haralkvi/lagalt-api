package no.noroff.lagalt.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link no.noroff.lagalt.models.User} entity
 */
@Data
public class UserDetails implements Serializable {
    private final String id;
    private final String name;
    private final String email;
    private final String description;
}