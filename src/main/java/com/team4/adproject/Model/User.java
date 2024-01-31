package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserLearningSchedule userLearningSchedule;

}