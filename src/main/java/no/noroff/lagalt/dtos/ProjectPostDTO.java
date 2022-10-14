package no.noroff.lagalt.dtos;

import lombok.Data;
import no.noroff.lagalt.models.ProjectCategory;
import no.noroff.lagalt.models.ProjectStatus;

@Data
public class ProjectPostDTO {
    private String name;
    private int owner;
    private ProjectStatus status;
    private ProjectCategory category;
}
