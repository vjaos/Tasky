package org.tasky.backend.entity;


import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne
    private IssuePriority priority;

    @OneToOne
    private IssueType type;
}
