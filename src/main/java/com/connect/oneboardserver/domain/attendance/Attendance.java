package com.connect.oneboardserver.domain.attendance;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private Member member;

    @Column
    private Integer status;     // 0 : 결석, 1 : 지각, 2 : 출석

    @Builder
    public Attendance(Lesson lesson, Member member, Integer status) {
        this.lesson = lesson;
        this.member = member;
        this.status = status;
    }
}
