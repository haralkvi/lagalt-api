package no.noroff.lagalt.dtos.details;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link no.noroff.lagalt.models.Application} entity
 */
@Data
public class ApplicationDetails implements Serializable {
    private final String user;
    private final int application_id;
    private final String time;
    private final String text;
}