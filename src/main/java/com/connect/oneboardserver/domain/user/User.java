package com.connect.oneboardserver.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String studentNumber;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String userType;    // S = 학생, T = 강의자

    @Column(length = 30, nullable = false)
    private String university;

    @Column(length = 30, nullable = false)
    private String major;

//    private Lecture lecture;

    @Builder
    public User(String studentNumber, String email, String password, String name, String userType, String university, String major) {
        this.studentNumber = studentNumber;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
        this.university = university;
        this.major = major;
    }
}
