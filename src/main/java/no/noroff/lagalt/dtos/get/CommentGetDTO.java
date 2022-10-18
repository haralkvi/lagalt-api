package no.noroff.lagalt.dtos.get;

import lombok.Data;

@Data
public class CommentGetDTO {

    private int id;
    private int project;
    private String user;
    private String text;
    private String time;

}