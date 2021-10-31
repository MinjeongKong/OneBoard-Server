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
    private String student_num;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(nullable = false)
    private int user_type;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String university;

    @Column(length = 30, nullable = false)
    private String major;

    @Column(length = 128)
    private String lecture_id;

    @Builder
    public Member(String student_num, String name, String password, int user_type, String email, String university, String major, String lecture_id) {
        this.student_num = student_num;
        this.name = name;
        this.password = password;
        this.user_type = user_type;
        this.email = email;
        this.university = university;
        this.major = major;
        this.lecture_id = lecture_id;
    }
}
