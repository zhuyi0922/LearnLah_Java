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
    @Column(name = "id", nullable = false)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_status")
    private Integer userStatus;

    @Column(name = "is_delete")
    private Integer isDelete;
    @Column(name = "user_role")
    private Integer userRole;

    @Column(name = "id_code")
    private String idCode;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Record> records = new ArrayList<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserLearningSchedule userLearningSchedule;

}