package kw.zeropick.member.service;

import kw.zeropick.common.domain.exception.ResourceNotFoundException;
import kw.zeropick.member.controller.response.MemberInfoResponse;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.domain.exception.ConfirmPasswordMismatchException;
import kw.zeropick.member.domain.exception.FieldUpdateException;
import kw.zeropick.member.domain.exception.InvalidMemberDataException;
import kw.zeropick.member.dto.MemberFieldDto;
import kw.zeropick.member.dto.MemberInfoChangeDto;
import kw.zeropick.member.dto.MemberJoinDto;
import kw.zeropick.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Member getById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", memberId));
    }


    //아직 인터페이스 구현 x, 추후에 구현 후 MemberService -> MemberServiceImpl로 수정 예정.
    @Transactional
    public Member join(MemberJoinDto memberJoinDto) {

        String passwordConfirm = memberJoinDto.getPasswordConfirm();
        if (!passwordConfirm.equals(memberJoinDto.getPassword())) {
            throw new ConfirmPasswordMismatchException();
        }

        Member joinMember = memberJoinDto.toEntity();
        memberJpaRepository.save(joinMember);
        return joinMember;
    }

    /* security 의존성 추가후 변경할 join 함수 */
//    @Transactional
//    public Long join(Member member) {
//        member.changeMemberPassword(passwordEncoder.encode(member.getPassword()));
//        memberJpaRepository.save(member);
//        return member.getId();
//    }

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = this.getById(memberId);

        if(member.getEmail() == null || member.getName() == null || member.getPhoneNumber() == null || member.getBirthDate() == null){
            throw new InvalidMemberDataException();
        }

        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .build();
    }

    public List<String> getMemberField(Long memberId){
        Member member = this.getById(memberId);
        return member.getField();
    }

    @Transactional
    public Member updateMemberField(Long memberId, MemberFieldDto memberFieldDto){
        Member member = this.getById(memberId);
        member.changeFieldInfo(memberFieldDto.getField());

        if(!member.getField().equals(memberFieldDto.getField())){
            throw new FieldUpdateException();
        }
        return member;
    }

    @Transactional
    public Member updateMemberInfo(Long memberId, MemberInfoChangeDto memberInfoChangeDto){
        Member member = this.getById(memberId);
        member.changeMemberInfo(memberInfoChangeDto.getPhoneNumber(), memberInfoChangeDto.getBirthDate(), memberInfoChangeDto.getMarketingAgree());
        if(member.getPhoneNumber() == null || member.getBirthDate() == null || member.getMarketingAgree() == null){
            throw new InvalidMemberDataException();
        }
        return member;
    }


}
