package com.connect.oneboardserver.domain.login;

import net.bytebuddy.asm.TypeReferenceAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String student_num);
}
