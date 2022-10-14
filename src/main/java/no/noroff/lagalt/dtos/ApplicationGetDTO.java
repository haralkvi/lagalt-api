package no.noroff.lagalt.dtos;

import lombok.Data;

@Data
public class ApplicationGetDTO {

    private int application_id;
    private String user;
    private int project;
    private String time;
}
