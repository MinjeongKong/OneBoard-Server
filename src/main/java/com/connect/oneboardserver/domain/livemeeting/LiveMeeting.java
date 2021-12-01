package com.connect.oneboardserver.domain.livemeeting;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LiveMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Lesson lesson;

    @Column(length = 30, nullable = false)
    private String session;

    @Column
    private String startDt;

    @Column
    private String endDt;

    @Builder
    public LiveMeeting(Lesson lesson, String session, String startDt, String endDt) {
        this.lesson = lesson;
        this.session = session;
        this.startDt = startDt;
        this.endDt = endDt;
    }
}
