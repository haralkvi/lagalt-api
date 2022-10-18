package no.noroff.lagalt.dtos.get;

import lombok.Data;
import no.noroff.lagalt.dtos.details.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class ProjectGetDTO {

    private int id;
    private String name;
    private UserDetails owner;
    private Set<UserDetails> members;
    private Set<UserDetails> userViews;
    private String category;
    private String status;
    private Collection<String> tags;
    private String summary;
    private Set<Integer> comments;
    private String image;
    private String link;
    private Set<Integer> applications;

}
