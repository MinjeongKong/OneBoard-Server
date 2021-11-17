package com.connect.oneboardserver.domain.lecture.lesson;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByLectureId(Long lectureId);
}
