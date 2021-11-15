package com.connect.oneboardserver.service.attendance;

import com.connect.oneboardserver.domain.attendance.Attendance;
import com.connect.oneboardserver.domain.attendance.AttendanceRepository;
import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.domain.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceFindAllLessonForStudentResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceFindForLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final MemberLectureRepository memberLectureRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;

    public ResponseDto findAllAttendance(Long lectureId) {
        // lectureId로 MemberLecture에서 해당 과목을 수강하는 학생 리스트 조회
        List<MemberLecture> memberLectureList = memberLectureRepository.findAllByLectureId(lectureId);

        List<Member> studentList = new ArrayList<>();
        for(MemberLecture ml : memberLectureList) {
            if(ml.getMember().getRoles().get(0).equals("ROLE_S")) {
                studentList.add(ml.getMember());
            }
        }

        // lectureId로 Lesson에서 해당 과목의 모든 수업 조회
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lectureId);

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
}
