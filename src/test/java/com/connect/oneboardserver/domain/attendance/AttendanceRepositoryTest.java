package com.connect.oneboardserver.domain.attendance;

import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.domain.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AttendanceRepositoryTest {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LessonRepository lessonRepository;


    @AfterEach
    void cleanUp() {
        attendanceRepository.deleteAll();
        lessonRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("출석 생성 및 조회")
    void createAttendance() {
        Random random = new Random();

        // given
        String studentNumber = "number" + random.nextInt(100);
        String name = "name" + random.nextInt(100);
        String password = "0000";
        String email = random.nextInt(100) + "@test.com";
        String userType = "S";
        String university = "univ" + random.nextInt(100);
        String major = "major" + random.nextInt(100);

        Member member = memberRepository.save(Member.builder()
                .studentNumber(studentNumber)
                .name(name)
                .password(password)
                .email(email)
                .userType(userType)
                .university(university)
                .major(major)
                .roles(Collections.singletonList(userType))
                .build());

        String title = "title" + random.nextInt(100);
        String date = LocalDateTime.now().toString();
        String note = "url" + random.nextInt(100);
        int type = 0;

        Lesson lesson = lessonRepository.save(Lesson.builder()
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .build());

        System.out.println("member : " + member);
        System.out.println("lesson : " + lesson);

        attendanceRepository.save(Attendance.builder()
                .lesson(lesson)
                .member(member)
                .result("출석")
                .build());

        // when
        Attendance attendance = attendanceRepository.findAll().get(0);

        // then
        assertThat(attendance.getMember().getEmail()).isEqualTo(email);
        assertThat(attendance.getLesson().getTitle()).isEqualTo(title);
        assertThat(attendance.getResult()).isEqualTo("출석");
    }

//    @Test
//    @DisplayName("특정 수업의 학생 출석 조회")
//    void findAttendanceByMemberAndLesson() {
//
//    }
}
