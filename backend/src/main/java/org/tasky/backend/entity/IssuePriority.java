package org.tasky.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.tasky.backend.entity.enums.Priority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "priorities")
@EqualsAndHashCode(of = {"id", "priority"})
public class IssuePriority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @OneToOne
    private Issue issue;
}
