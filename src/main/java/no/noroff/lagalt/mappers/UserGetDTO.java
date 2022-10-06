package no.noroff.lagalt.mappers;

import lombok.Data;

import java.util.Set;

@Data
public class UserGetDTO {

    private String name;
    private String email;
    private String description;
    //private String skillSet;
    private Set<Integer> projectsOwned;

    //skal disse være med?
    private boolean hidden;
    private boolean admin;

}
