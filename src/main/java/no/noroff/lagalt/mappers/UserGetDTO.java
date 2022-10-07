package no.noroff.lagalt.mappers;

import lombok.Data;

import java.util.Set;

@Data
public class UserGetDTO {

    private int id;
    private String name;
    private String email;
    private String description;
    //private String skillSet;
    private Set<Integer> projectsOwned;
    //legg til disse hvis nødvendig
    //private Set<Integer> projectsHistory;
    //private Set<Integer> projetsParticipated;
    //skal disse være med?
    private boolean hidden;
    private boolean admin;

}
