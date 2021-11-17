package com.connect.oneboardserver.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByMemberIdAndLessonId(Long memberId, Long lessonId);

    void deleteAllByLessonId(Long lessonId);
}
