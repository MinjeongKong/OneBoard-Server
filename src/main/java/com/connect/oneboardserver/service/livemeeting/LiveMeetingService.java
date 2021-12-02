package com.connect.oneboardserver.service.livemeeting;

import com.connect.oneboardserver.domain.livemeeting.LiveMeeting;
import com.connect.oneboardserver.domain.livemeeting.LiveMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class LiveMeetingService {

    private final LiveMeetingRepository liveMeetingRepository;

    public LiveMeeting createLiveMeeting(Long lectureId) {
        String session = "session_" + lectureId + "_" + Instant.now().getEpochSecond();

        return liveMeetingRepository.save(LiveMeeting.builder()
                .session(session)
                .build());
    }
}