package com.aimskr.ac2.kakao.backend.member.service;

import com.aimskr.ac2.kakao.backend.member.domain.Member;
import com.aimskr.ac2.kakao.backend.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssignRuleService {
    private final MemberRepository memberRepository;

    // 최근배당
    private int lastQa = 0;

    @Transactional(readOnly = true)
    public String getQaAssign() {
        List<Member> qaMembers = memberRepository.getQaMembers();
        // 운영팀 부재 시 AIP로 배당
        if (qaMembers.size() == 0) return "AIP";
        lastQa = calcNextIndex(lastQa, qaMembers.size());
        return qaMembers.get(lastQa).getName();
    }

    public int calcNextIndex(int curr, int size) {
        curr++;
        if (curr >= size) curr = 0;
        return curr;
    }
}
