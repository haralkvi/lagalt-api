package no.noroff.lagalt.dtos;

import lombok.Data;

@Data
public class ApplicationGetDTO {

    private int application_id;
    private int user;
    private String project;
    private String status;
    private String time;
}
