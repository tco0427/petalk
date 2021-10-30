package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.dto.request.UpdatePasswordRequest;
import dankook.capstone.petalk.dto.response.*;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.dto.request.SignInRequest;
import dankook.capstone.petalk.dto.request.UpdateMemberRequest;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.S3Uploader;
import dankook.capstone.petalk.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;

    /**
     * 회원 조회
     */
    @ApiOperation(value = "", notes = "토큰 받아서 회원 정보 조회")
    @GetMapping("/lookup")
    public ResponseData<MemberDto> getMember(HttpServletRequest httpServletRequest){
        ResponseData<MemberDto> responseData = null;

        MemberDto memberDto;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);

            memberDto = new MemberDto(member);

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,memberDto);

            log.info(responseData.toString());
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 회원 수정
     */
    @ApiOperation(value = "", notes = "회원 정보 수정")
    @PutMapping("/update")
    public ResponseData<UpdateMemberResponse> updateMember(HttpServletRequest httpServletRequest,
                                                           @RequestBody @Valid UpdateMemberRequest request){
        ResponseData<UpdateMemberResponse> responseData = null;
        UpdateMemberResponse updateMemberResponse;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            memberService.update(memberId, request.getName(), request.getNickname(), request.getEmail());
            Member member = memberService.findOne(memberId);

            updateMemberResponse = new UpdateMemberResponse(member.getId(),member.getName(),member.getEmail(),member.getNickname());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updateMemberResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 비밀번호 변경
     */
    @ApiOperation(value = "", notes = "비밀번호 변경")
    @PutMapping("/password/update")
    public ResponseData<UpdatePasswordResponse> updatePassword(HttpServletRequest httpServletRequest,
                                                               @RequestBody @Valid UpdatePasswordRequest request) {
        ResponseData<UpdatePasswordResponse> responseData = null;
        UpdatePasswordResponse updatePasswordResponse;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);

            if(memberService.validateMember(member, request.getOldPassword())){
                memberService.updatePassword(memberId, request.getNewPassword());
                responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, null);
            }else {throw new NoSuchElementException();}

        }catch(NoSuchElementException e) {
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e) {
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 프로필 이미지 변경
     */
    @ApiOperation(value = "", notes = "프로필 이미지 변경")
    @PutMapping("/image/update")
    public ResponseData<UpdateImageResponse> updateImageMember(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        ResponseData<UpdateImageResponse> responseData = null;
        UpdateImageResponse updateImageResponse;

        try {
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            String url = s3Uploader.upload(multipartFile, "static");

            memberService.updateImage(memberId, url);

            updateImageResponse = new UpdateImageResponse(memberId, url);

        } catch(NoSuchElementException e) {
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 회원 삭제
     */
    @ApiOperation(value = "", notes = "회원 정보 삭제")
    @DeleteMapping("/delete")
    public ResponseData<DeleteMemberDto> deleteMember(HttpServletRequest httpServletRequest){
        ResponseData<DeleteMemberDto> responseData;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            memberService.deleteById(memberId);
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,new DeleteMemberDto(memberId));
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_USER, null);
            log.error(e.getMessage());
        }
        return responseData;
    }
}
