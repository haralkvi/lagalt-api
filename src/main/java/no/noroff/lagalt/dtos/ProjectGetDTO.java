package no.noroff.lagalt.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class ProjectGetDTO {

    private int id;
    private String name;
    private String owner;
    //private Set<Integer> members;
    //private Set<Integer> userViews;
    private String category;
    private String tags;
    private String summary;
    //private Set<Integer> comments;
    private String image;
    private String link;
    //private Set<Integer> applications;

}
