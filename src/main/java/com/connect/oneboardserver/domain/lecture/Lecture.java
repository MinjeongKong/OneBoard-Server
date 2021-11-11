package com.connect.oneboardserver.domain.lecture;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String lecturePlan;

    @Column(length = 30)
    private String semester;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    @Builder
    public Lecture(String title, String lecturePlan, String semester, List<Notice> notices) {
        this.title = title;
        this.lecturePlan = lecturePlan;
        this.semester = semester;
        this.notices = notices;
    }

    public void updateLecturePlan(String lecturePlan) {
        this.lecturePlan = lecturePlan;
    }
}
