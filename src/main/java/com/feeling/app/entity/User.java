package com.feeling.app.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    public User() { }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
