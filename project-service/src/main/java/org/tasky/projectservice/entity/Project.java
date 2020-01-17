package org.tasky.projectservice.entity;


import lombok.Data;

import javax.persistence.*;


@Entity(name = "Project")
@Table(name = "project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;

}
