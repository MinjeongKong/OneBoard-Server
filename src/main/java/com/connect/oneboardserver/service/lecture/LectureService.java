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
        List<MemberLecture> lectureOfMemberList = memberLectureRepository.findAllByMemberId(member.getId());

        List<LectureFindResponseDto> responseDtoList = new ArrayList<>();

        for (int i = 0; i < lectureOfMemberList.size(); i++) {
            Lecture lectureOfMember = lectureOfMemberList.get(i).getLecture();

            LectureFindResponseDto responseDto = LectureFindResponseDto.toResponseDto(lectureOfMember);
            responseDto.setProfessor(findProfessor(lectureOfMember.getId()));

            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }

    public ResponseDto findLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        LectureFindResponseDto responseDto = LectureFindResponseDto.toResponseDto(lecture);
        responseDto.setProfessor(findProfessor(lectureId));

        return new ResponseDto("SUCCESS", responseDto);
    }

    private String findProfessor(Long lectureId) {
        String professorName = null;
        List<MemberLecture> memberOfLectureList = memberLectureRepository.findAllByLectureId(lectureId);
        for(int i = 0; i < memberOfLectureList.size(); i++) {
            Member memberOfLecture = memberOfLectureList.get(i).getMember();
            if(memberOfLecture.getRoles().get(0).equals("ROLE_T")) {
                professorName = memberOfLecture.getName();
                break;
            }
        }
        return professorName;
    }
}
