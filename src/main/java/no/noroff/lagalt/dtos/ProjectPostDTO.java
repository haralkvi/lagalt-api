package no.noroff.lagalt.dtos;

import lombok.Data;

@Data
public class ProjectPostDTO {
    private String name;
    private int owner;
    private String category;
}
