package no.noroff.lagalt.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApplicationPostDTO {
    private int user;
    private int project;
    private String time;
}
