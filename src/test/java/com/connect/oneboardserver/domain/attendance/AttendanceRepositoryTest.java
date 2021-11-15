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
import java.util.List;
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

    Random random = new Random();

    @AfterEach
    void cleanUp() {
        attendanceRepository.deleteAll();
        lessonRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("출석 생성 및 조회")
    void createAttendance() {
        // given
        Lesson expectedLesson = createLesson();
        Member expectedMember = createMember();
        String expectedResult = "출석";

        attendanceRepository.save(Attendance.builder()
                .lesson(expectedLesson)
                .member(expectedMember)
                .result(expectedResult)
                .build());

        // when
        Attendance actualAttendance = attendanceRepository.findAll().get(0);

        // then
        assertThat(actualAttendance.getLesson().getTitle()).isEqualTo(expectedLesson.getTitle());
        assertThat(actualAttendance.getMember().getEmail()).isEqualTo(expectedMember.getEmail());
        assertThat(actualAttendance.getResult()).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("특정 수업의 학생 출석 조회")
    void findAttendanceByMemberAndLesson() {
        // given
        Lesson expectedLesson1 = createLesson();
        Lesson expectedLesson2 = createLesson();
        Member expectedMember = createMember();
        String expectedResult1 = "출석";
        String expectedResult2 = "결석";

        attendanceRepository.save(Attendance.builder()
                .lesson(expectedLesson1)
                .member(expectedMember)
                .result(expectedResult1)
                .build());

        attendanceRepository.save(Attendance.builder()
                .lesson(expectedLesson2)
                .member(expectedMember)
                .result(expectedResult2)
                .build());

        // when
        List<Attendance> actualAttendanceList
                = attendanceRepository.findAllByLessonIdAndMemberId(expectedLesson1.getId(), expectedMember.getId());

        // then
        assertThat(actualAttendanceList.size()).isEqualTo(1);
        assertThat(actualAttendanceList.get(0).getLesson().getTitle()).isEqualTo(expectedLesson1.getTitle());
        assertThat(actualAttendanceList.get(0).getMember().getEmail()).isEqualTo(expectedMember.getEmail());
        assertThat(actualAttendanceList.get(0).getResult()).isEqualTo(expectedResult1);
    }

    private Member createMember() {
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

        return member;
    }

    private Lesson createLesson() {
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

        return lesson;
    }
}
