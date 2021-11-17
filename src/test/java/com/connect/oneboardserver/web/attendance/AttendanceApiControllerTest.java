package com.connect.oneboardserver.web.attendance;

import com.connect.oneboardserver.domain.attendance.Attendance;
import com.connect.oneboardserver.domain.attendance.AttendanceRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendFindAllForStuResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceUpdateAllRequestDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AttendanceApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MemberLectureRepository memberLectureRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    Random random = new Random();

    @AfterEach
    void tearDown() {
        attendanceRepository.deleteAll();
        lessonRepository.deleteAll();
        memberLectureRepository.deleteAll();
        lectureRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("과목 전체 출석 조회 요청")
    void requestFindAllAttendance() {
        // given
        List<Member> expectedMemberList = new ArrayList<>();
        expectedMemberList.add(createMember("S"));
        expectedMemberList.add(createMember("S"));
        Lecture lecture = createLecture();
        for(Member member : expectedMemberList) {
            registerMemberLecture(member, lecture);
        }
        List<Lesson> expectedLessonList = new ArrayList<>();
        expectedLessonList.add(createLesson(lecture));
        expectedLessonList.add(createLesson(lecture));
        for(Member member : expectedMemberList) {
            for(Lesson lesson : expectedLessonList) {
                initAttendance(member, lesson);
            }
        }

        String url = "http://localhost:" + port + "/lecture/{lectureId}/attendance";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, lecture.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AttendFindAllForStuResponseDto> responseDtoList
                = (List<AttendFindAllForStuResponseDto>) responseEntity.getBody().getData();

        // responseDtoList.get(i): LinkedHashMap
        ObjectMapper mapper = new ObjectMapper();

        for(int i = 0; i < responseDtoList.size(); i++) {
            AttendFindAllForStuResponseDto responseDto
                    = mapper.convertValue(responseDtoList.get(i), AttendFindAllForStuResponseDto.class);

            assertThat(responseDto.getStudentId()).isEqualTo(expectedMemberList.get(i).getId());
            assertThat(responseDto.getAttendanceList().get(i).getLessonId()).isEqualTo(expectedLessonList.get(i).getId());
            assertThat(responseDto.getAttendanceList().get(i).getStatus()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("과목 전체 출석 수정 요청")
    void requestUpdateAllAttendance() {
        // given
        Member expectedMember = createMember("S");
        Lecture lecture = createLecture();
        registerMemberLecture(expectedMember, lecture);
        Lesson expectedLesson = createLesson(lecture);
        initAttendance(expectedMember, expectedLesson);

        Integer expectedStatus = 2; // 출석

        List<AttendanceUpdateRequestDto> updateList = new ArrayList<>();
        updateList.add(AttendanceUpdateRequestDto.builder()
                .studentId(expectedMember.getId())
                .lessonId(expectedLesson.getId())
                .status(expectedStatus)
                .build());
        AttendanceUpdateAllRequestDto requestDto = new AttendanceUpdateAllRequestDto(updateList);


        String url = "http://localhost:" + port + "/lecture/{lectureId}/attendance";

        HttpEntity<AttendanceUpdateAllRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseDto.class, lecture.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Attendance> actualAttendances
                = attendanceRepository.findAllByMemberIdAndLessonId(expectedMember.getId(), expectedLesson.getId());
        assertThat(actualAttendances.size()).isEqualTo(1);
        assertThat(actualAttendances.get(0).getStatus()).isEqualTo(expectedStatus);
    }

    private Member createMember(String type) {
        String studentNumber = "number" + random.nextInt(100);
        String name = "name" + random.nextInt(100);
        String password = "0000";
        String email = random.nextInt(100) + "@test.com";
        String university = "univ" + random.nextInt(100);
        String major = "major" + random.nextInt(100);


        Member member = memberRepository.save(Member.builder()
                .studentNumber(studentNumber)
                .name(name)
                .password(password)
                .email(email)
                .userType(type)
                .university(university)
                .major(major)
                .roles(Collections.singletonList("ROLE_" + type))
                .build());

        return member;
    }

    private Lecture createLecture() {
        String title = "lecture" + random.nextInt(100);

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(title)
                .build());

        return lecture;
    }

    private void registerMemberLecture(Member member, Lecture lecture) {
        memberLectureRepository.save(MemberLecture.builder()
                .member(member)
                .lecture(lecture)
                .build());
    }

    private Lesson createLesson(Lecture lecture) {
        String title = "title" + random.nextInt(100);
        String date = LocalDateTime.now().toString();
        String note = "url" + random.nextInt(100);
        int type = 0;

        Lesson lesson = lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .build());

        return lesson;
    }

    private void initAttendance(Member member, Lesson lesson) {
        attendanceRepository.save(Attendance.builder()
                .member(member)
                .lesson(lesson)
                .status(0)  // 결석
                .build());
    }
}
