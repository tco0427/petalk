package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "", notes = "신규 회원 생성")
    @PostMapping("/new")
    public ResponseData<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request){
        ResponseData<CreateMemberResponse> responseData;
        CreateMemberResponse createMemberResponse = null;

        try{

            Member member = new Member();
            member.setUserId(request.getUserId());
            member.setName(request.getName());
            member.setPassword(request.getPassword());
            member.setProfileUrl(request.getProfileUrl());
            member.setEmail(request.getEmail());
            member.setPetList(request.getPetList());

            Long id = memberService.join(member);

            createMemberResponse = new CreateMemberResponse(id,member.getUserId(),member.getName(),member.getEmail());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,createMemberResponse);

        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL,createMemberResponse);
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
        private String profileUrl;
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
