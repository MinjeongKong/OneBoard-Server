package com.connect.oneboardserver.domain.assignment;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Assignment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300)
    private String content;

    @Column(length = 128)
    private String fileUrl;

    @Column(nullable = false)
    private String startDt;

    @Column(nullable = false)
    private String endDt;

    @Column
    private String exposeDt;

    @Column(nullable = false)
    private Float score;

    @Builder
    public Assignment(String title, String content, String fileUrl, String startDt, String endDt, String exposeDt, Float score) {
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
        this.startDt = startDt;
        this.endDt = endDt;
        this.exposeDt = exposeDt;
        this.score = score;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public void update(String title, String content, String fileUrl, String startDt, String endDt, String exposeDt, Float score) {
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
        this.startDt = startDt;
        this.endDt = endDt;
        this.exposeDt = exposeDt;
        this.score = score;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
