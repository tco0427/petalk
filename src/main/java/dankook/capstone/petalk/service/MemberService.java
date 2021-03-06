package dankook.capstone.petalk.service;

import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.PlatformType;
import dankook.capstone.petalk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //읽기만 하는 경우 readOnly를 true해주는게 성능상 이점이 있음
@RequiredArgsConstructor    //Rombok, final 있는 필드만 가지고 생성자 만들어줌.
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long joinWithKakao(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);    //중복 회원 검증
        String encodedPassword = passwordEncoder.encode(member.getPassword());  //비밀번호 암호화
        member.setPassword(encodedPassword);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOneByUserId(String userId){
        return memberRepository.findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findAll();
        for (Member findMember : findMembers) {
            if(member.getUserId().equals(findMember.getUserId())){
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }
    }

    public Boolean validateMember(Member member, String password) {
        String memberPassword = member.getPassword();

        if(!passwordEncoder.matches(password, memberPassword)) {
            return false;
        }else{
            return true;
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void updateImage(Long id, String url) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        member.updateImage(url);
    }

    @Transactional
    public void update(Long id, String name,String nickname, String email){
        Member member = memberRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        member.updateMember(name, nickname, email);
    }

    @Transactional
    public void updatePassword(Long id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        password = passwordEncoder.encode(password);

        member.updatePassword(password);
    }

    @Transactional
    public void deleteById(Long id){
        memberRepository.deleteById(id);
    }

    public Member findByPlatformId(Integer platformId)
    {
        Optional<Member> optionalUser = memberRepository.findByPlatformId(platformId);
        if(optionalUser.isEmpty()){
            return null;
        }

        return optionalUser.get();
    }
}

