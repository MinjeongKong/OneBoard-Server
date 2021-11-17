package com.connect.oneboardserver.domain.relation;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class GradeRatioLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private GradeRatio gradeRatio;

    @Builder
    public GradeRatioLecture(Lecture lecture, GradeRatio gradeRatio) {
        this.lecture = lecture;
        this.gradeRatio = gradeRatio;
    }
}
