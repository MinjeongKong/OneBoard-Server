package com.connect.oneboardserver.domain.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String studentNumber;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String user_type;    // S = 학생, T = 강의자

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String university;

    @Column(length = 30, nullable = false)
    private String major;

    @Column(length = 128)
    private String lecture_id;

    @Builder
    public Member(String studentNumber, String name, String password, String user_type, String email, String university, String major, String lecture_id) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.password = password;
        this.user_type = user_type;
        this.email = email;
        this.university = university;
        this.major = major;
        this.lecture_id = lecture_id;
    }
}
