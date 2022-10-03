package no.noroff.lagalt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="character")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private int id;
    @Column(name="project_name")
    private String name;
    @OneToOne
    private User owner;
    @ManyToMany
    private Set<User> members;
    @Column(name="project_category")
    private String category;
    @Column(name="tags")
    private String tags;
    @Column(name="summary")
    private String summary;
    //@OneToMany(mappedBy = "")
    //private Comments comments;
}
