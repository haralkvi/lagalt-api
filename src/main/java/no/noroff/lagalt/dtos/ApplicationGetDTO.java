package no.noroff.lagalt.mappers;

import lombok.Data;

@Data
public class ApplicationGetDTO {

    private int application_id;
    private int user;
    private int project;
    private String status;
    private String time;
}
