package com.connect.oneboardserver.domain;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberLectureRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    MemberLectureRepository memberLectureRepository;

    @AfterEach
    void cleanUp() {
        memberLectureRepository.deleteAll();
        lectureRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("멤버-과목 등록 및 조회")
    void registerLectureToMember() {
        // given
        String studentNumber = "12345678";
        String name = "member";
        String password = "0000";
        String email = "test@ajou.ac.kr";
        String user_type = "S";
        String university = "Ajou";
        String major = "Software";

        Member member = memberRepository.save(Member.builder()
                .studentNumber(studentNumber)
                .name(name)
                .password(password)
                .email(email)
                .userType(user_type)
                .university(university)
                .major(major)
                .roles(Collections.singletonList(user_type))
                .build());

        String title = "lecture";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(title)
                .build());

        memberLectureRepository.save(MemberLecture.builder()
                .member(member)
                .lecture(lecture)
                .build());


        // when
        List<MemberLecture> memberLectureList = memberLectureRepository.findAll();

        // then
        assertThat(memberLectureList.get(0).getMember().getEmail()).isEqualTo(email);
        assertThat(memberLectureList.get(0).getLecture().getTitle()).isEqualTo(title);
    }
}
