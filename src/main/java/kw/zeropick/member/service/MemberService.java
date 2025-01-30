package kw.zeropick.member.service;

import jakarta.persistence.EntityNotFoundException;
import kw.zeropick.common.domain.exception.ResourceNotFoundException;
import kw.zeropick.member.controller.response.MemberInfoResponse;
import kw.zeropick.member.controller.response.MypageInfoResponse;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.domain.MemberInterest;
import kw.zeropick.member.domain.exception.ConfirmPasswordMismatchException;
import kw.zeropick.member.domain.exception.FieldUpdateException;
import kw.zeropick.member.domain.exception.InvalidMemberDataException;
import kw.zeropick.member.dto.MemberFieldDto;
import kw.zeropick.member.dto.MemberInfoChangeDto;
import kw.zeropick.member.dto.MemberJoinDto;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.repository.BookmarkJpaRepository;
import kw.zeropick.review.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final BookmarkJpaRepository bookmarkRepository;
    private final ReviewJpaRepository reviewRepository;

    public Member getById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", memberId));
    }

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. ID: " + memberId));

        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .interest(member.getInterest())
                .diabetes(member.getDiabetes())
                .build();
    }

    public MemberInfoResponse updateMemberInfo(Long memberId, MemberInfoChangeDto memberInfoChangeDto) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. ID: " + memberId));

        member.setNickname(memberInfoChangeDto.getNickname());
        member.setInterest(memberInfoChangeDto.getInterest());
        member.setDiabetes(memberInfoChangeDto.getDiabetes());

        memberJpaRepository.save(member);

        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .interest(member.getInterest())
                .diabetes(member.getDiabetes())
                .build();
    }


    public MemberInterest getMemberInterest(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. ID: " + memberId));

        return member.getInterest();
    }

    public MypageInfoResponse getMypageInfo(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. ID: " + memberId));

        Long bookmarkCount = bookmarkRepository.countByMember(member);
        Long reviewCount = reviewRepository.countByMember(member);

        return MypageInfoResponse.builder()
                .nickname(member.getNickname())
                .bookmarkCount(bookmarkCount)
                .reviewCount(reviewCount)
                .build();
    }

}
