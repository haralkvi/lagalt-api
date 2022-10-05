package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    private String password;

    @Column(name = "user_desciption")
    private String description;

    @ElementCollection
    private Set<String> skillSet;

    @OneToMany
    private Set<Project> projectsOwned;

    @ManyToMany
    private Set<Project> projectsParticipated;

    @ManyToMany
    private Set<Project> projectsHistory;

    private boolean admin;

    private boolean hidden;
}