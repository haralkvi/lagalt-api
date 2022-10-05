package no.noroff.lagalt.models;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public String text;

    @Timestamp
    public String time;


}
