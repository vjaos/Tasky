package org.tasky.backend.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_status")
    private IssueStatus currentStatus;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_issues",
            joinColumns = {@JoinColumn(name = "issue_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


}
