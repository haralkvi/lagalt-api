package no.noroff.lagalt.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommentPostDTO {
    //private int project;
    //private int user;
    private String text;
    private String time;
}
