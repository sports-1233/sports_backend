package com.sport.backend.member.repository;

import com.sport.backend.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    boolean existsByUserId(String userId);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    Optional<Member> findByUserId(String userId);
}