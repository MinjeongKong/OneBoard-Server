package com.connect.oneboardserver.domain.livemeeting;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LiveMeetingRepositoryTest {

    @Autowired
    LiveMeetingRepository liveMeetingRepository;

    @Autowired
    LessonRepository lessonRepository;

    Random random = new Random();

    @AfterEach
    void tearDown() {
        lessonRepository.deleteAll();
        liveMeetingRepository.deleteAll();
    }

    @Test
    @DisplayName("비대면 수업 저장 및 조회")
    void saveLiveMeeting() {
        // given
        LiveMeeting expectedLiveMeeting = createLiveMeeting();

        liveMeetingRepository.save(expectedLiveMeeting);

        // when
        List<LiveMeeting> liveMeetingList = liveMeetingRepository.findAll();

        // then
        LiveMeeting actualLiveMeeting = liveMeetingList.get(0);
        assertThat(actualLiveMeeting.getSession()).isEqualTo(expectedLiveMeeting.getSession());
        assertThat(actualLiveMeeting.getStartDt()).isEqualTo(expectedLiveMeeting.getStartDt());
        assertThat(actualLiveMeeting.getEndDt()).isEqualTo(expectedLiveMeeting.getEndDt());
    }

    @Test
    @DisplayName("수업에서 비대면 수업 조회")
    void findLiveMeetingInLesson() {
        // given
        LiveMeeting expectedLiveMeeting = liveMeetingRepository.save(createLiveMeeting());
        Lesson expectedLesson = createLesson(expectedLiveMeeting);

        // when
        List<Lesson> lessonList = lessonRepository.findAll();

        // then
        Lesson actualLesson = lessonList.get(0);
        assertThat(actualLesson.getId()).isEqualTo(expectedLesson.getId());
        assertThat(actualLesson.getLiveMeeting().getId()).isEqualTo(expectedLiveMeeting.getId());
        assertThat(actualLesson.getLiveMeeting().getSession()).isEqualTo(expectedLiveMeeting.getSession());
    }

    private LiveMeeting createLiveMeeting() {
        String expectedSession = "session" + random.nextInt(100);
        String expectedStartDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String expectedEndDt = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return LiveMeeting.builder()
                .session(expectedSession)
                .startDt(expectedStartDt)
                .endDt(expectedEndDt)
                .build();
    }

    private Lesson createLesson(LiveMeeting liveMeeting) {
        String title = "title" + random.nextInt(100);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int type = 1;

        return lessonRepository.save(Lesson.builder()
                .title(title)
                .date(date)
                .type(type)
                .liveMeeting(liveMeeting)
                .build());
    }
}