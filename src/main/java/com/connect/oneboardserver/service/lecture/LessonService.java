package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.livemeeting.LiveMeeting;
import com.connect.oneboardserver.service.attendance.AttendanceService;
import com.connect.oneboardserver.service.livemeeting.LiveMeetingService;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
    private final LessonRepository lessonRepository;
    private final LectureRepository lectureRepository;
    private final AttendanceService attendanceService;
    private final LiveMeetingService liveMeetingService;

    public ResponseDto findLessonList(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        List<Lesson> lessonList = lessonRepository.findAllByLectureIdOrderByDateDesc(lecture.getId());

        List<LessonFindResponseDto> responseDtoList = new ArrayList<>();
        for(Lesson lesson : lessonList) {
            LessonFindResponseDto responseDto = LessonFindResponseDto.toResponseDto(lesson);
            if(lesson.getNoteUrl() != null) {
                String loadNoteUrl = "/lecture/" + lectureId + "/lesson/" + lesson.getId() + "/note";
                responseDto.setNoteUrl(loadNoteUrl);
            }
            if(lesson.getLiveMeeting() != null) {
                responseDto.setSession(lesson.getLiveMeeting().getSession());
            }
            responseDtoList.add(responseDto);
        }

        return new ResponseDto("SUCCESS", responseDtoList);
    }

    public ResponseDto findLesson(Long lectureId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다 : id = " + lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            throw new IllegalArgumentException("올바른 과목 id와 수업 id가 아닙니다 : lecture id = " + lectureId
                    + " lesson id = " + lessonId);
        }

        LessonFindResponseDto responseDto = LessonFindResponseDto.toResponseDto(lesson);
        if(lesson.getNoteUrl() != null) {
            String loadNoteUrl = "/lecture/" + lectureId + "/lesson/" + lessonId + "/note";
            responseDto.setNoteUrl(loadNoteUrl);
        }
        if(lesson.getLiveMeeting() != null) {
            responseDto.setSession(lesson.getLiveMeeting().getSession());
        }

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto createLesson(Long lectureId, LessonCreateRequestDto requestDto, MultipartFile file) throws Exception {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        Lesson lesson = requestDto.toEntity();
        lesson.setLecture(lecture);

        switch(lesson.getType()) {
            case 0 :
                lesson.setRecordingLesson(requestDto.getVideoUrl());
                break;
            case 1 :
                LiveMeeting liveMeeting = liveMeetingService.createLiveMeeting(lectureId);
                lesson.setNonFaceToFaceLesson(liveMeeting);
                break;
            case 2 :
                lesson.setFaceToFaceLesson(requestDto.getRoom());
                break;
            default :
                throw new IllegalArgumentException("유효한 수업 타입이 아닙니다 : type = " + lesson.getType());
        }

        Lesson savedLesson = lessonRepository.save(lesson);

        if(file != null) {
            String path = "/lecture_" + lectureId + "/lesson_" + savedLesson.getId() + "/note";
            String uploadedFilePath = storageService.store(path, file);

            savedLesson.updateNoteUrl(uploadedFilePath);
        }

        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);

        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(savedLesson.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto updateLesson(Long lectureId, Long lessonId, LessonUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다 : id = " + lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            throw new IllegalArgumentException("올바른 과목 id와 수업 id가 아닙니다 : lecture id = " + lectureId
                    + " lesson id = " + lessonId);
        }
        // 수업 기본 정보 업데이트
        lesson.update(requestDto.getTitle(), requestDto.getDate(), requestDto.getType());
        // 수업 타입별 정보 업데이트
        switch(lesson.getType()) {
            case 0 :
                if(lesson.getLiveMeeting() != null) {
                    liveMeetingService.deleteLiveMeeting(lesson.getLiveMeeting().getId());
                }
                lesson.setRecordingLesson(requestDto.getVideoUrl());
                break;
            case 1 :
                if(lesson.getLiveMeeting() == null) {
                    LiveMeeting liveMeeting = liveMeetingService.createLiveMeeting(lectureId);
                    lesson.setNonFaceToFaceLesson(liveMeeting);
                }
                break;
            case 2 :
                if(lesson.getLiveMeeting() != null) {
                    liveMeetingService.deleteLiveMeeting(lesson.getLiveMeeting().getId());
                }
                lesson.setFaceToFaceLesson(requestDto.getRoom());
                break;
            default :
                throw new IllegalArgumentException("유효한 수업 타입이 아닙니다 : type = " + lesson.getType());
        }
        // 기존 수업 강의노트 삭제
        if(lesson.getNoteUrl() != null) {
            if(storageService.delete(lesson.getNoteUrl())) {
                lesson.updateNoteUrl(null);
            }
        }
        // 수업 강의노트 업데이트
        if(file != null) {
            String path = "/lecture_" + lectureId + "/lesson_" + lesson.getId() + "/note";
            String uploadedFilePath = storageService.store(path, file);

            lesson.updateNoteUrl(uploadedFilePath);
        }

        LessonUpdateResponseDto responseDto = LessonUpdateResponseDto.builder()
                .lessonId(lesson.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto deleteLesson(Long lectureId, Long lessonId) throws IOException {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            if (lesson.getNoteUrl() != null) {
                System.out.println(lesson.getNoteUrl());
                storageService.delete(lesson.getNoteUrl());
            }
            attendanceService.deleteLessonAttendance(lesson.getId());
            lessonRepository.deleteById(lessonId);
            return new ResponseDto("SUCCESS");
        }
    }

    @Transactional
    public ResponseDto createLessonForTest(Long lectureId, LessonCreateRequestDto requestDto) {
        Lecture lecture = null;
        Lesson savedLesson = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        Integer Type = requestDto.getType();
        if (Type == 0) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
//                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .videoUrl(requestDto.getVideoUrl())
                    .build());
        }
        if (Type == 1) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
//                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
//                    .meetingId(requestDto.getMeetingId())
                    .build());
        }
        if (Type == 2) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
//                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .room(requestDto.getRoom())
                    .build());
        }
        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);

        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(savedLesson.getId())
                .build();

        return new ResponseDto("SUCCESS" ,responseDto);
    }

    @Transactional
    public ResponseDto updateLessonForTest(Long lectureId, long lessonId, LessonUpdateRequestDto requestDto) {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            lesson.update(requestDto.getTitle(), requestDto.getDate(), requestDto.getType());
            LessonUpdateResponseDto responseDto = LessonUpdateResponseDto.builder()
                    .lessonId(lesson.getId())
                    .build();
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto findLessonDefaultInfo(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()-> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lectureId);
        String lessonDefaultTitle = lecture.getTitle() + " 수업 " + (lessonList.size() + 1);

        List<String[]> defaultDateTimeList = parseDefaultDateTime(lecture.getDefaultDateTime());
        String nextLessonDateTime = getNextLessonDateTime(defaultDateTimeList);

        LessonFindDefaultResponseDto responseDto = LessonFindDefaultResponseDto.builder()
                .defaultTitle(lessonDefaultTitle)
                .defaultDateTime(nextLessonDateTime)
                .defaultRoom(lecture.getDefaultRoom())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    private int getDayOfWeekValue(String day) {
        String[] dayOfWeek = {"월", "화", "수", "목", "금", "토", "일"};
        return Arrays.asList(dayOfWeek).indexOf(day) + 1;
    }

    private List<String[]> parseDefaultDateTime(String defaultDateTime) {
        String[] defaultDateTimes = defaultDateTime.split(", ");

        List<String[]> defaultDateTimeList = new ArrayList<>();
        for(int i = 0; i < defaultDateTimes.length; i++) {
            String[] splitStr = defaultDateTimes[i].split("[ :-]");     // 월 12:00-13:30
            defaultDateTimeList.add(splitStr);
        }
        return defaultDateTimeList;
    }

    private String getNextLessonDateTime(List<String[]> defaultDateTimeList) {
        LocalDateTime nextLessonDateTime = null;

        LocalDateTime now = LocalDateTime.now();
        int todayOfWeek = now.getDayOfWeek().getValue();

        for(int i = 0; i < defaultDateTimeList.size(); i++) {
            String[] defaultDateTime = defaultDateTimeList.get(i);

            int dateGap = getDayOfWeekValue(defaultDateTime[0]) - todayOfWeek;
            boolean isNowBefore = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond())
                    .isBefore(LocalTime.of(Integer.valueOf(defaultDateTime[1]), Integer.valueOf(defaultDateTime[2])));
            if((dateGap > 0) || (dateGap == 0 && isNowBefore)) {
                nextLessonDateTime = now.plusDays(dateGap);
                nextLessonDateTime = nextLessonDateTime
                        .withHour(Integer.valueOf(defaultDateTime[1]))
                        .withMinute(Integer.valueOf(defaultDateTime[2]))
                        .withSecond(0);
                break;
            }
        }
        if(nextLessonDateTime == null) {
            String[] defaultDateTime = defaultDateTimeList.get(0);
            int dateGap = getDayOfWeekValue(defaultDateTime[0]) - todayOfWeek;

            nextLessonDateTime = now.plusDays(7 - Math.abs(dateGap));
            nextLessonDateTime = nextLessonDateTime
                    .withHour(Integer.valueOf(defaultDateTime[1]))
                    .withMinute(Integer.valueOf(defaultDateTime[2]))
                    .withSecond(0);
        }

        return nextLessonDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}