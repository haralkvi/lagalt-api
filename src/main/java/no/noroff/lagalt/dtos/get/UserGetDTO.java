package no.noroff.lagalt.dtos.get;

import lombok.Data;

import java.util.Set;

@Data
public class UserGetDTO {

    private String id;
    private String name;
    private String email;
    private String description;
    private Set<String> skillSet;
    private Set<Integer> projectsOwned;
    private Set<Integer> projectsHistory;
    private Set<Integer> projectsParticipated;
    private boolean hidden;
    private boolean admin;

}
