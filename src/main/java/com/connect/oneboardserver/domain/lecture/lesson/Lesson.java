package com.connect.oneboardserver.domain.lecture.lesson;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(length = 128, nullable = false)
    private String note;

    @Column(nullable = false)
    private int type;

    @Column(length = 30)
    private String room;

    @Column(length = 128)
    private String meeting_id;

    @Column(length = 128)
    private String video_url;

    @Builder
    public Lesson(Lecture lecture, String title, LocalDateTime date, String note, int type, String room, String meeting_id, String video_url) {
        this.lecture = lecture;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meeting_id = meeting_id;
        this.video_url = video_url;
    }
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
