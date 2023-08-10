package com.onboarid.wanted.user.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter @Builder
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public User() {

    }
}
