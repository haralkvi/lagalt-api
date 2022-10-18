package no.noroff.lagalt.dtos.get;

import lombok.Data;
import no.noroff.lagalt.dtos.details.ProjectDetails;
import no.noroff.lagalt.dtos.details.UserDetails;

@Data
public class ApplicationGetDTO {

    private int application_id;
    private UserDetails user;
    private ProjectDetails project;
    private String time;
}
