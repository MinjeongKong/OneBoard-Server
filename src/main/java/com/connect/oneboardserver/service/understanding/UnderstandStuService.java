package com.connect.oneboardserver.service.understanding;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import com.connect.oneboardserver.domain.understanding.UnderstandProRepository;
import com.connect.oneboardserver.domain.understanding.UnderstandStu;
import com.connect.oneboardserver.domain.understanding.UnderstandStuRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.understanding.ResultResponseDto;
import com.connect.oneboardserver.web.dto.understanding.UnderstandStuCreateRequestDto;
import com.connect.oneboardserver.web.dto.understanding.UnderstandStuFindResponseDto;
import com.connect.oneboardserver.web.dto.understanding.UnderstandStuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UnderstandStuService {

    private final UnderstandProRepository understandProRepository;
    private final UnderstandStuRepository understandStuRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Transactional
    public ResponseDto createUnderstandStu(Long lectureId, Long lessonId,
                                           Long understandProId, ServletRequest request, UnderstandStuCreateRequestDto requestDto) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        UnderstandPro understandPro = understandProRepository.findById(understandProId)
                .orElseThrow(()->new IllegalArgumentException("해당 평가가 없습니다. id="+understandProId));

        if (!(understandPro.getLesson().getId().equals(lessonId)&&understandPro.getLesson().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            UnderstandStu understandStu = requestDto.toEntity();
            understandStu.setUnderstandPro(understandPro);
            understandStu.setStudent(student);

            understandStuRepository.save(understandStu);
            UnderstandStuResponseDto responseDto = new UnderstandStuResponseDto(understandStu);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto findResult(Long lectureId, Long lessonId, Long understandProId) {
        UnderstandPro understandPro = understandProRepository.findById(understandProId)
                .orElseThrow(()->new IllegalArgumentException("해당 평가가 없습니다. id="+understandProId));

        if (!(understandPro.getLesson().getId().equals(lessonId)&&understandPro.getLesson().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            List<UnderstandStu> understandStuOList = understandStuRepository.findAllByUnderstandProIdAndResponse(understandProId, 1);
            List<UnderstandStuFindResponseDto> understandOList = new ArrayList<>();
            for (int i = 0; i < understandStuOList.size(); i++) {
                understandOList.add(new UnderstandStuFindResponseDto(understandStuOList.get(i)));
            }

            List<UnderstandStu> understandStuXList = understandStuRepository.findAllByUnderstandProIdAndResponse(understandProId, 0);
            List<UnderstandStuFindResponseDto> understandXList = new ArrayList<>();
            for (int i = 0; i < understandStuXList.size(); i++) {
                understandXList.add(new UnderstandStuFindResponseDto(understandStuXList.get(i)));
            }

            understandPro.updateInfo(understandOList.size(), understandXList.size());
            ResultResponseDto responseDto = new ResultResponseDto(understandPro, understandPro.getYes(), understandPro.getNo(), understandOList, understandXList);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }


}
