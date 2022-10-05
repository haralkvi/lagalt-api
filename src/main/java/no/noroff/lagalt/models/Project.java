package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private int id;

    @Column(name="project_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "projectsOwned")
    private User owner;

    @ManyToMany(mappedBy = "projectsParticipated")
    private Set<User> members;

    @ManyToMany(mappedBy = "projectsHistory")
    private Set<User> userViews;

    @Column(name="project_category")
    private String category;

    @Column(name="tags")
    private String tags;

    @Column(name="summary")
    private String summary;

    @OneToMany(mappedBy = "project")
    private Set<Comment> comments;

    @Column(name="project_image")
    private String image;

    @Column(name="project_link")
    private String link;

    @OneToMany(mappedBy = "project")
    private Set<Application> applications;
}
