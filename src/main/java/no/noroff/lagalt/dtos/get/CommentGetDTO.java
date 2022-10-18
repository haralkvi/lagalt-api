package no.noroff.lagalt.dtos.get;

import lombok.Data;
import no.noroff.lagalt.dtos.details.ProjectDetails;
import no.noroff.lagalt.dtos.details.UserDetails;

@Data
public class CommentGetDTO {

    private int id;
    private ProjectDetails project;
    private UserDetails user;
    private String text;
    private String time;

}
