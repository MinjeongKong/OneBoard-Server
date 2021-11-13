package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureFindResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final UserDetailsService userDetailsService;
    private final LectureRepository lectureRepository;
    private final MemberLectureRepository memberLectureRepository;

    public ResponseDto createLecture(LectureCreateRequestDto requestDto) {
        Member member = (Member) userDetailsService.loadUserByUsername(requestDto.getEmail());
        Lecture lecture = lectureRepository.save(requestDto.toEntity());

        memberLectureRepository.save(MemberLecture.builder()
                .member(member)
                .lecture(lecture)
                .build());

        LectureCreateResponseDto responseDto = LectureCreateResponseDto.builder()
                .lectureId(lecture.getId())
                .build();
        return new ResponseDto("SUCCESS", responseDto);
    }

    public ResponseDto findLectureList(String email) {
        Member member = (Member) userDetailsService.loadUserByUsername(email);
        List<MemberLecture> memberLectureList = memberLectureRepository.findAllByMemberId(member.getId());

        List<LectureFindResponseDto> lectureFindResponseDtoList = new ArrayList<>();
        for (int i = 0; i < memberLectureList.size(); i++) {
            LectureFindResponseDto lectureFindResponseDto
                    = LectureFindResponseDto.toResponseDto(memberLectureList.get(i).getLecture());
//            lectureFindResponseDto.setProfessor();
            lectureFindResponseDtoList.add(lectureFindResponseDto);
        }
        return new ResponseDto("SUCCESS", lectureFindResponseDtoList);
    }

    public ResponseDto findLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        LectureFindResponseDto responseDto = LectureFindResponseDto.toResponseDto(lecture);

        List<MemberLecture> memberList = memberLectureRepository.findAllByLectureId(lectureId);
        for(int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i).getMember();
            if(member.getRoles().get(0).equals("ROLE_T")) {
                responseDto.setProfessor(member.getName());
                break;
            }
        }

        return new ResponseDto("SUCCESS", responseDto);
    }
}
