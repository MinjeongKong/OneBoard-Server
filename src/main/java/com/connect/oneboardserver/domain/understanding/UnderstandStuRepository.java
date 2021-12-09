package com.connect.oneboardserver.domain.understanding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnderstandStuRepository extends JpaRepository<UnderstandStu, Long> {
    List<UnderstandStu> findAllByUnderstandProIdAndResponse(Long understandProId, Integer response);

}
