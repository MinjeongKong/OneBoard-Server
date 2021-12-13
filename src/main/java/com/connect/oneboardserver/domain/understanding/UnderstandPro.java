package com.connect.oneboardserver.domain.understanding;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.lecture.Lecture;
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
public class UnderstandPro extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lesson lesson;

    @Column
    private Integer yes;

    @Column
    private Integer no;

    @Builder
    public UnderstandPro(Lesson lesson) {
        this.lesson = lesson;
    }

    public void updateInfo(Integer yes, Integer no) {
        this.yes = yes;
        this.no = no;
    }
}
