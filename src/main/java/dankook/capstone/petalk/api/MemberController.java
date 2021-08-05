package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 생성
     */
    @PostMapping("/new")
    public ResponseData<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request){
        ResponseData<CreateMemberResponse> responseData = null;
        CreateMemberResponse createMemberResponse = null;

        try{
            Member member = new Member();
            member.setUserId(request.getUserId());
            member.setName(request.getName());
            member.setPassword(request.getPassword());
            member.setProfile(request.getProfile());
            member.setEmail(request.getEmail());
            member.setPetList(request.getPetList());

            Long id = memberService.join(member);
            createMemberResponse = new CreateMemberResponse(member.getId(),member.getUserId(),member.getName(),member.getEmail());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,createMemberResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.FAIL,createMemberResponse);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }


    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
        private String userId;
        private String name;
        private String email;
    }

    @Data
    static class CreateMemberRequest{
        private String userId;
        private String password;
        private String name;
        private String email;
        private File profile;
        private List<Pet> petList=new ArrayList<>();
    }
    /**
     * 회원 조회
     */

    /**
     * 회원 수정
     */

    /**
     * 회원 삭제
     */


}
