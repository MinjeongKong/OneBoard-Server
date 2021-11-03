package com.connect.oneboardserver.domain.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Column
    private LocalDateTime exposeDt;

    @CreatedDate
    private LocalDateTime createdDt;

    @LastModifiedDate
    private LocalDateTime updatedDt;

    @Builder
    public Notice(Lecture lecture, String title, String content, LocalDateTime exposeDt) {
        this.lecture = lecture;
        this.title = title;
        this.content = content;
        this.exposeDt = exposeDt;
    }
}
