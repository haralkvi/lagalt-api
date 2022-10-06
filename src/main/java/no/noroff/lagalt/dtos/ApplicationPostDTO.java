package no.noroff.lagalt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationPostDTO {
    private int user;
    private int project;
    private String status;
    private String time;
}
