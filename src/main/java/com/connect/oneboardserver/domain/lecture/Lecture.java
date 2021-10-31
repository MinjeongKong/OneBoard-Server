package com.connect.oneboardserver.domain.lecture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 128)
    private String lecture_plan;

    @Column(length = 30)
    private String semester;

    @Builder
    public Lecture(String title, String lecture_plan, String semester) {
        this.title = title;
        this.lecture_plan = lecture_plan;
        this.semester = semester;
    }
}
