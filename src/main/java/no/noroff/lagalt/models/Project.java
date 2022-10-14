package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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

    @Enumerated(EnumType.STRING)
    @Column(name="project_category")
    private ProjectCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name="project_status")
    private ProjectStatus status;

    @ElementCollection
    @Column(name="tags")
    private Collection<String> tags;

    @Column(name="summary")
    private String summary;

    @OneToMany(mappedBy = "project")
    private Set<Comment> comments;

    @Column(name="project_link")
    private String link;

    @OneToMany(mappedBy = "project")
    private Set<Application> applications;

    public boolean removeUserFromHistory(User user) {
        return userViews.remove(user);
    }

    public boolean removeUserFromMembers(User user) {
        return members.remove(user);
    }
}
