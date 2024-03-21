package com.aimskr.ac2.hana.backend.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
     * 이름순으로 오름차순 정렬
     */
    @Query("SELECT m FROM Member m ORDER BY m.name ASC")
    List<Member> findAllDesc();

    /*
     * QA 작업 가능한 멤버 조회
     */
    @Query("SELECT m FROM Member m where m.isQaAssign = TRUE")
    List<Member> getQaMembers();

    Optional<Member> findByAccount(String account);

    Optional<Member> findByName(String name);

    List<Member> findByRole(MemberRole role);
}
