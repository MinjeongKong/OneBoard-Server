package com.connect.oneboardserver.domain.quiz;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class QuizPro extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lesson lesson;

    @Column(length = 300, nullable = false)
    private String question;

    @Column(length = 30, nullable = false)
    private String answer1;

    @Column(length = 30, nullable = false)
    private String answer2;

    @Column(length = 30, nullable = false)
    private String answer3;

    @Column(length = 30, nullable = false)
    private String answer4;

    @Column(length = 30, nullable = false)
    private String answer5;

    @Column(nullable = false)
    private Integer answer;

    @Column
    private Integer correctNum;

    @Column
    private Integer incorrectNum;

    @Builder
    public QuizPro(String question, String answer1, String answer2, String answer3, String answer4, String answer5, Integer answer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.answer = answer;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void updateInfo(Integer correctNum, Integer incorrectNum) {
        this.correctNum = correctNum;
        this.incorrectNum = incorrectNum;
    }

    public String getAnswerStr(Integer answerNum) {
        switch (answerNum) {
            case 1:
                return answer1;
            case 2:
                return answer2;
            case 3:
                return answer3;
            case 4:
                return answer4;
            case 5:
                return answer5;
            default:
                return null;
        }
    }
}
