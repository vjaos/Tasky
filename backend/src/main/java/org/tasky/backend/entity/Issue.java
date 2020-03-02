package org.tasky.backend.entity;


import lombok.Data;

import javax.persistence.*;

@Data
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

}
