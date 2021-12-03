package com.connect.oneboardserver.service.livemeeting;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.livemeeting.LiveMeeting;
import com.connect.oneboardserver.domain.livemeeting.LiveMeetingRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class LiveMeetingService {

    private final LiveMeetingRepository liveMeetingRepository;
    private final LessonRepository lessonRepository;
    private final UserDetailsService userDetailsService;

    public LiveMeeting createLiveMeeting(Long lectureId) {
        String session = "session_" + lectureId + "_" + Instant.now().getEpochSecond();

        return liveMeetingRepository.save(LiveMeeting.builder()
                .session(session)
                .build());
    }

    public void deleteLiveMeeting(Long liveMeetingId) {
        LiveMeeting liveMeeting = liveMeetingRepository.findById(liveMeetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 비대면 수업이 없습니다 : id = " + liveMeetingId));
        liveMeetingRepository.deleteById(liveMeeting.getId());
    }

    @Transactional
    public ResponseDto enterLiveMeeting(String email, Long lectureId, Long lessonId, String session) throws Exception {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다 : id = " + lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            throw new IllegalArgumentException("올바른 과목 id와 수업 id가 아닙니다 : lecture id = " + lectureId
                    + " lesson id = " + lessonId);
        }

        if(!lesson.getLiveMeeting().getSession().equals(session)) {
            throw new IllegalArgumentException("올바른 session이 아닙니다 : session = " + session);
        }
        // 종료된 비대면 수업에 대해 강의자는 비대면 수업 초기화 및 입장할 수 있는지 or 강의자도 입장 불가?
        // 종료된 비대면 수업이 있는 수업의 경우 강의자가 삭제 불가 & 다른 타입으로 수정 불가한지?
        if(lesson.getLiveMeeting().getEndDt() != null) {
            throw new Exception("이미 종료된 비대면 수업입니다");
        }

        Member member = (Member) userDetailsService.loadUserByUsername(email);

        if(lesson.getLiveMeeting().getStartDt() == null) {
            if(member.getRoles().get(0).equals("ROLE_S")) {
                throw new Exception("아직 시작되지 않은 비대면 수업입니다");
            } else {
                /*
                 * 소켓 생성 및 연결 코드 작성
                 */
                lesson.getLiveMeeting().startLiveMeeting();
                return new ResponseDto("SUCCESS");
            }
        } else {
            /*
             * 소켓 생성 및 연결 코드 작성
             */
            return new ResponseDto("SUCCESS");
        }
    }
}