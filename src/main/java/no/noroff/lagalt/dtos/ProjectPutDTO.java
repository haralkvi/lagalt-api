package no.noroff.lagalt.dtos;

import lombok.Data;
import no.noroff.lagalt.models.ProjectCategory;
import no.noroff.lagalt.models.ProjectStatus;

import java.io.Serializable;

/**
 * A DTO for the {@link no.noroff.lagalt.models.Project} entity
 */
@Data
public class ProjectPutDTO implements Serializable {
    private final int id;
    private final String name;
    private final ProjectCategory category;
    private final ProjectStatus status;
    private final String summary;
    private final String link;
}