package com.example.oauthserver.member.repository;

import com.example.oauthserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /* 이미 있는 멤버인지 확인 */
    Optional<Member> findMemberById(@Param("id") String id);

}

