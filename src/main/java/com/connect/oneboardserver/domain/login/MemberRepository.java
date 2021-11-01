package com.connect.oneboardserver.domain.login;

import net.bytebuddy.asm.TypeReferenceAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmail(String email);
}
