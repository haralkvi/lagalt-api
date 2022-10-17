package no.noroff.lagalt.dtos;

import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class ProjectGetDTO {

    private int id;
    private String name;
    private String owner;
    private Set<String> members;
    private Set<String> userViews;
    private String category;
    private String status;
    private Collection<String> tags;
    private String summary;
    private Set<Integer> comments;
    private String image;
    private String link;
    private Set<String> applicants;

}
