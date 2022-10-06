package no.noroff.lagalt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDTO {
    private int project;
    private int user;
    private String text;
    private String time;
}
