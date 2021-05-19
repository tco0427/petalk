package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기만 하는 경우 readOnly를 true해주는게 성능상 이점이 있음
//@AllArgsConstructor //Rombok, 모든 필드 가지고 생성자 만들어줌.
@RequiredArgsConstructor    //Rombok, final 있는 필드만 가지고 생성자 만들어줌.
public class MemberService {

    //MemberRepository는 변경할 일이 없기 때문에 필드를 final로 선언하는 것을 권장함
    private final MemberRepository memberRepository;


    /*
    //@Autowired  //생성자 인젝션, 생성자가 단 하나인 경우 해당 어노테이션 생략해도 됨
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

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

    //멀티 쓰레드 고려 x(만약 두 사람이 동시에 insert하면?)
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findByName(member.getName());
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