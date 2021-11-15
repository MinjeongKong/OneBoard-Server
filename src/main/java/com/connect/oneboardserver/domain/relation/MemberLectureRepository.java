package com.connect.oneboardserver.domain.relation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberLectureRepository extends JpaRepository<MemberLecture, Long> {
    List<MemberLecture> findAllByMemberId(Long memberId);

    List<MemberLecture> findAllByLectureId(Long lectureId);
}
