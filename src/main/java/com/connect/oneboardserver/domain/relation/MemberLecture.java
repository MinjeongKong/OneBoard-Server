package com.connect.oneboardserver.domain.relation;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MemberLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Lecture lecture;

    @Builder
    public MemberLecture(Member member, Lecture lecture) {
        this.member = member;
        this.lecture = lecture;
    }
}
