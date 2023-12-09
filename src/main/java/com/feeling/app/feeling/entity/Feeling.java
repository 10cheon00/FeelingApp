package com.feeling.app.feeling.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class Feeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate createdDate;

    @Column
    private Long userId;

    @Column
    private String description;

    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Timestamp createdTimestamp;

    public Feeling() { }

    public Feeling(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }


    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
