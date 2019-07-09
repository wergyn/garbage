package com.training.model;

import com.training.enums.Action;
import com.training.enums.State;
import com.training.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "desired_resolution_date")
    private LocalDate desiredDate;

    @Transient
    private List<Action> actions;

    public List<Action> getActions() {
        if (actions == null) {
            actions = Collections.emptyList();
        }
        return actions;
    }

    @Column(name = "state_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "urgency_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;
}
