package com.connect.oneboardserver.domain.quiz;

import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class QuizStu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QuizPro quizPro;

    @ManyToOne
    private Member student;

    @Column(nullable = false)
    private Integer response;

    @Column(length = 30)
    private String mark;

    @Builder
    public QuizStu(Integer response) {
        this.response = response;
    }

    public void setQuizPro(QuizPro quizPro) {
        this.quizPro = quizPro;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setStudent(Member student) {
        this.student = student;
    }
}
