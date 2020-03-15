package org.tasky.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "task_status")
public class IssueStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_name")
    private String statusName;

}
