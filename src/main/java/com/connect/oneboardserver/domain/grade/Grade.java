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

    @ManyToOne
    private Member student;

    @Column
    private Float totalScore;

    @Column
    private Float submitScore;

    @Column
    private Float attendScore;

    @Column
    private String result;

    @Column
    private String changeResult;

    @Builder
    public Grade(Lecture lecture, Member student, Float totalScore, Float submitScore, Float attendScore, String result, String changeResult) {
        this.lecture = lecture;
        this.student = student;
        this.totalScore = totalScore;
        this.submitScore = submitScore;
        this.attendScore = attendScore;
        this.result = result;
        this.changeResult = changeResult;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setChangeResult(String changeResult) {
        this.changeResult = changeResult;
    }

    public void updateScore(Float submitScore, Float attendScore, Float totalScore) {
        this.submitScore = submitScore;
        this.attendScore = attendScore;
        this.totalScore = totalScore;
    }

    public void init() {
        this.submitScore = 0f;
        this.attendScore = 0f;
        this.totalScore = 0f;
        this.result = null;
        this.changeResult = null;
    }
}
