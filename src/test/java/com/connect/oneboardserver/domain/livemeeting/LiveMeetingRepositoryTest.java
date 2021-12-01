package com.connect.oneboardserver.domain.livemeeting;

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

    Random random = new Random();

    @AfterEach
    void tearDown() {
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

}