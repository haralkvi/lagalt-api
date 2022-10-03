package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="character")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String name;

    private String password;

    private Set<String> skillSet;

    @OneToMany
    private Set<Project> projectsOwned;

    @ManyToMany
    private Set<Project> projectsParticipated;

    @ManyToMany
    private Set<Project> projectsHistory;

    private boolean admin;
}