package no.noroff.lagalt.models;


import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private int application_id;

    @OneToOne
    private User user;

    @OneToOne
    private Project project;

    @Column(name="application_status")
    private String status;

    @Timestamp
    public String time;




}
