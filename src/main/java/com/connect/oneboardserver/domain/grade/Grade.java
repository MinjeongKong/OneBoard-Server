package com.connect.oneboardserver.domain.grade;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @OneToOne
    private Member student;

    @Column
    private Float score;

    @Column
    private String grade;

    @Builder
    public Grade(Lecture lecture, Member student, Float score, String grade) {
        this.lecture = lecture;
        this.student = student;
        this.score = score;
        this.grade = grade;
    }
}
