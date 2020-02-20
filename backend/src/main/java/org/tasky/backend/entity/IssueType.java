package org.tasky.backend.entity;

import lombok.Data;
import org.tasky.backend.entity.enums.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "types")
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @OneToOne
    private Issue issue;
}
