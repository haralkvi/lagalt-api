package no.noroff.lagalt.dtos.get;

import lombok.Data;
import no.noroff.lagalt.dtos.details.ProjectDetails;

import java.util.Set;

@Data
public class UserGetDTO {

    private String id;
    private String name;
    private String email;
    private String description;
    private Set<String> skillSet;
    private Set<ProjectDetails> projectsOwned;
    private Set<ProjectDetails> projectsHistory;
    private Set<ProjectDetails> projectsParticipated;
    private boolean hidden;
    private boolean admin;

}
