package no.noroff.lagalt.dtos.post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommentPostDTO {
    private int project;
    private String user;
    private String text;
    private String time;
}
