package com.connect.oneboardserver.domain.lesson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LessonRepositoryTest {

    @Autowired
    LessonRepository lessonRepository;

    @AfterEach
    public void cleanUp() {
        lessonRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성 및 불러오기")
    public void createLesson() {
        // given
        String title = "Test Title";
        LocalDateTime date = LocalDateTime.now();
        String note = "lesson note file url";
        int type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";

        lessonRepository.save(Lesson.builder()
                .title(title)
                .date(date).note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build());

        // when
        List<Lesson> lessonList = lessonRepository.findAll();

        // then
        Lesson lesson = lessonList.get(0);
        assertThat(lesson.getTitle()).isEqualTo(title);
//        assertThat(lesson.getDate()).isEqualTo(date);
    }
}