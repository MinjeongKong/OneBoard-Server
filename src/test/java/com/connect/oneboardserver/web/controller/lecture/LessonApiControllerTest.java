package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    public void tearDown() throws Exception {
        lessonRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성하기")
    public void requestCreateLesson() {
        // given
        String lectureTitle = "lecture";
        String lecturePlanUrl = "url";
        String semester = "2021-2";

        Long lectureId = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build()).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String noteUrl = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meetingId = "zoom meeting url";
        String videoUrl = "lesson video url";

        LessonCreateRequestDto requestDto = LessonCreateRequestDto.builder()
                .title(title)
                .date(date)
                .noteUrl(noteUrl)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class, lectureId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();

        LessonCreateResponseDto responseDto = mapper.convertValue(responseData, LessonCreateResponseDto.class);

        Lesson newLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();


        assertThat(newLesson.getLecture().getTitle()).isEqualTo(lectureTitle);
        assertThat(newLesson.getLecture().getId()).isEqualTo(lectureId);
        assertThat(newLesson.getNoteUrl()).isEqualTo(noteUrl);
        assertThat(newLesson.getType()).isEqualTo(type);
        assertThat(newLesson.getRoom()).isEqualTo(room);
        assertThat(newLesson.getMeetingId()).isEqualTo(meetingId);
        assertThat(newLesson.getVideoUrl()).isEqualTo(videoUrl);
    }

    @Test
    @DisplayName("수업 조회 요청")
    void requestFindLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build());

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String noteUrl = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meetingId = "zoom meeting url";
        String videoUrl = "lesson video url";

        Long lessonId = lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date)
                .noteUrl(noteUrl)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build()).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, lecture.getId(), lessonId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();
        ObjectMapper mapper = new ObjectMapper();
        LessonFindResponseDto responseDto = mapper.convertValue(responseData, LessonFindResponseDto.class);


        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getLectureId()).isEqualTo(lecture.getId());
        assertThat(responseDto.getNoteUrl()).isEqualTo(noteUrl);
        assertThat(responseDto.getMeetingId()).isEqualTo(meetingId);
        assertThat(responseDto.getVideoUrl()).isEqualTo(videoUrl);
        assertThat(responseDto.getRoom()).isEqualTo(room);
        assertThat(responseDto.getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("수업 삭제 요청")
    void requestDeleteLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String noteUrl = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meetingId = "zoom meeting url";
        String videoUrl = "lesson video url";

        Long lessonId = lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date)
                .noteUrl(noteUrl)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build()).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        restTemplate.delete(url, lectureId, lessonId);

        // then
        assertThat(lessonRepository.findById(lessonId)).isEmpty();
    }
    @Test
    @DisplayName("수업 수정 요청")
    void requestUpdateLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String noteUrl = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meetingId = "zoom meeting url";
        String videoUrl = "lesson video url";


        Lesson lesson = Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date).noteUrl(noteUrl)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build();

        Long lessonId = lessonRepository.save(lesson).getId();

        String updateTitle = "Test Title2";
        String updateDate = LocalDateTime.now().toString();
        String updateNoteUrl = "lesson note file url2";
        Integer updateType = 2;
        String updateRoom = "Paldal 411";
        String updateMeetingId = "zoom meeting url2";
        String updateVideoUrl = "lesson video url2";

        LessonUpdateRequestDto requestDto = LessonUpdateRequestDto.builder()
                .title(updateTitle)
                .date(updateDate)
                .noteUrl(updateNoteUrl)
                .type(updateType)
                .room(updateRoom)
                .meetingId(updateMeetingId)
                .videoUrl(updateVideoUrl)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        HttpEntity<LessonUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseDto.class, lectureId, lessonId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        LessonUpdateResponseDto responseDto = mapper.convertValue(responseData, LessonUpdateResponseDto.class);

        Lesson updatedLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();

        assertThat(updatedLesson.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedLesson.getType()).isEqualTo(updateType);
        assertThat(updatedLesson.getNoteUrl()).isEqualTo(updateNoteUrl);
        assertThat(updatedLesson.getRoom()).isEqualTo(updateRoom);
        assertThat(updatedLesson.getMeetingId()).isEqualTo(updateMeetingId);
        assertThat(updatedLesson.getVideoUrl()).isEqualTo(updateVideoUrl);
    }

}