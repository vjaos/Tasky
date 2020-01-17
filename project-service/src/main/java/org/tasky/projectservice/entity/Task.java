package org.tasky.projectservice.entity;


import lombok.Data;

import javax.persistence.*;


@Entity(name = "Task")
@Table(name = "task")
@Data
public class Task {


    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
}
