package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="person")
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_description", columnDefinition = "text")
    private String description;

    @ElementCollection
    private Set<String> skillSet;

    @OneToMany(mappedBy = "owner")
    private Set<Project> projectsOwned;

    @ManyToMany
    @JoinTable(
            name = "user_projects_membership",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projectsParticipated;

    @ManyToMany
    @JoinTable(
            name = "user_projects_click_history",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projectsHistory;

    private boolean hidden;

    @OneToMany(mappedBy = "user")
    private Set<Application> applications;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
}