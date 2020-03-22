package org.tasky.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.tasky.backend.entity.enums.IssueStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {


    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_status")
    private IssueStatus issueStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


}
