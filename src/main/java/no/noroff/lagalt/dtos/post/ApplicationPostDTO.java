package no.noroff.lagalt.dtos.post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApplicationPostDTO {
    private String user;
    private int project;
    private String time;
    private String text;
}
