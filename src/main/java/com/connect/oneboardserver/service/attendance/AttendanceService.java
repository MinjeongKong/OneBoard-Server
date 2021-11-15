package com.connect.oneboardserver.service.attendance;

import com.connect.oneboardserver.domain.attendance.Attendance;
import com.connect.oneboardserver.domain.attendance.AttendanceRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.domain.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceFindAllLessonForStudentResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceFindForLesson;
import com.connect.oneboardserver.web.dto.attendance.AttendanceUpdateAllRequestDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final LectureRepository lectureRepository;
    private final MemberLectureRepository memberLectureRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;

    public ResponseDto findAllAttendance(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        // lectureId로 MemberLecture에서 해당 과목을 수강하는 학생 리스트 조회
        List<MemberLecture> memberLectureList = memberLectureRepository.findAllByLectureId(lecture.getId());

        List<Member> studentList = new ArrayList<>();
        for(MemberLecture ml : memberLectureList) {
            if(ml.getMember().getRoles().get(0).equals("ROLE_S")) {
                studentList.add(ml.getMember());
            }
        }

        // lectureId로 Lesson에서 해당 과목의 모든 수업 조회
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lecture.getId());

        List<AttendanceFindAllLessonForStudentResponseDto> responseDtoList = new ArrayList<>();
        // 학생을 기준으로 Attendance에서 학생과 모든 수업에 대해서 조회
        for(Member student : studentList) {
            AttendanceFindAllLessonForStudentResponseDto responseDto
                    = AttendanceFindAllLessonForStudentResponseDto.builder()
                    .studentId(student.getId())
                    .studentNumber(student.getStudentNumber())
                    .studentName(student.getName())
                    .build();
            List<AttendanceFindForLesson> attendanceList = new ArrayList<>();
            for(Lesson lesson : lessonList) {
                Attendance attendance = attendanceRepository.findAllByLessonIdAndMemberId(lesson.getId(), student.getId()).get(0);
                AttendanceFindForLesson result = AttendanceFindForLesson.builder()
                        .lessonId(lesson.getId())
                        .lessonDate(lesson.getDate())
                        .status(attendance.getStatus())
                        .build();
                attendanceList.add(result);
            }
            responseDto.setAttendanceList(attendanceList);
            responseDtoList.add(responseDto);
        }

        return new ResponseDto("SUCCESS", responseDtoList);
    }

    @Transactional
    public ResponseDto updateAllAttendance(Long lectureId, AttendanceUpdateAllRequestDto requestDto) {
        for(AttendanceUpdateRequestDto updateData : requestDto.getUpdateDataList()) {
            List<Attendance> attendances
                    = attendanceRepository.findAllByLessonIdAndMemberId(updateData.getLessonId(), updateData.getStudentId());
            if(attendances.size() != 1) {
                // 동일한 학생 & 수업에 대해 출석 데이터가 없거나 2개 이상인 경우
                return new ResponseDto("FAIL");
            }
            attendances.get(0).updateStatus(updateData.getStatus());
        }

        return new ResponseDto("SUCCESS");
    }
}
