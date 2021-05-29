package dankook.capstone.petalk.controller;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Member member=new Member();

        member.setId(memberForm.getId());
        member.setPassword(memberForm.getPassword());
        member.setName(memberForm.getName());
        member.setEmail(memberForm.getEmail());
        member.setProfile(memberForm.getProfile());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
