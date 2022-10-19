package no.noroff.lagalt.dtos.details;

import lombok.Data;
import no.noroff.lagalt.models.ProjectCategory;
import no.noroff.lagalt.models.ProjectStatus;

import java.io.Serializable;
import java.util.Collection;

/**
 * A DTO for the {@link no.noroff.lagalt.models.Project} entity
 */
@Data
public class ProjectDetails implements Serializable {
    private final int id;
    private final String name;
    private final ProjectCategory category;
    private final ProjectStatus status;
    private final Collection<String> tags;
    private final Collection<String> skillsNeeded;
    private final String summary;
    private final String link;
}