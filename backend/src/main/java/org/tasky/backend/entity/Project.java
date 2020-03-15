package org.tasky.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
@EqualsAndHashCode(of = {"name", "description"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Issue> issues = new ArrayList<>();

    public boolean containsIssue(String issueTitle) {
        return issues.stream().anyMatch(it -> it.getTitle().equals(issueTitle));
    }

}
