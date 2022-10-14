package no.noroff.lagalt.dtos;

import lombok.Data;

@Data
public class ApplicationGetDTO {

    private int application_id;
    private int user;
    private int project;
    private String time;
}
