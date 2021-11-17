package com.connect.oneboardserver.domain.relation;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GradeRatioLectureRepository extends JpaRepository<GradeRatioLecture, Long> {

    GradeRatioLecture findByLectureId(Long lectureId);
}
