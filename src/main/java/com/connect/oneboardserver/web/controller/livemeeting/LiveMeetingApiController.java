package com.connect.oneboardserver.web.controller.livemeeting;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.service.livemeeting.LiveMeetingService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LiveMeetingApiController {

    private final LiveMeetingService liveMeetingService;
    private final JwtTokenProvider jwtTokenProvider;


    // 비대면 수업 입장
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/live/entrance")
    public ResponseDto enterLiveMeeting(@PathVariable Long lectureId, @PathVariable Long lessonId,
                                           @RequestParam("session") String session, HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getUserPk(token);

        return liveMeetingService.enterLiveMeeting(email, lectureId, lessonId, session);
    }

    // 비대면 수업 퇴장 - 학생

    // 비대면 수업 종료 - 강의자
}
