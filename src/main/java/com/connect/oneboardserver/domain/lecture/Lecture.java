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

    @Column(length = 30)
    private String semester;

    @Column(length = 128)
    private String lecturePlanUrl;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    @Builder
    public Lecture(String title, String semester, String lecturePlanUrl, List<Notice> notices) {
        this.title = title;
        this.semester = semester;
        this.lecturePlanUrl = lecturePlanUrl;
        this.notices = notices;
    }

    public void updateLecturePlanUrl(String lecturePlanUrl) {
        this.lecturePlanUrl = lecturePlanUrl;
    }
}
