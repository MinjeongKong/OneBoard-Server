package com.connect.oneboardserver.web;

import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.domain.lesson.LessonRepository;
import com.connect.oneboardserver.web.dto.LessonCreateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

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

    @AfterEach
    public void tearDown() throws Exception {
        lessonRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성하기")
    public void requestCreateLesson() {
        // given
        String title = "Test Title";
        LocalDateTime date = LocalDateTime.now();
        String note = "lesson note file url";
        int type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";

        LessonCreateRequestDto requestDto = LessonCreateRequestDto.builder()
                .title(title)
                .date(date).note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build();

        String url = "http://localhost:" + port + "/lecture/lesson";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        System.out.println("responseEntity");
        System.out.println(responseEntity);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Lesson> all = lessonRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getDate()).isEqualTo(date);
    }
}