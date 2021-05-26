package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기만 하는 경우 readOnly를 true해주는게 성능상 이점이 있음
@RequiredArgsConstructor    //Rombok, final 있는 필드만 가지고 생성자 만들어줌.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @return
     */
    @Transactional
    public String join(Member member){
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findById(member.getId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

