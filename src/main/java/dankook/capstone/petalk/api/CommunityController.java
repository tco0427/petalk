package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.dto.response.CommunityDto;
import dankook.capstone.petalk.entity.Comment;
import dankook.capstone.petalk.entity.Community;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.dto.request.CreateCommunityRequest;
import dankook.capstone.petalk.dto.request.UpdateCommunityRequest;
import dankook.capstone.petalk.dto.response.CreateCommunityResponse;
import dankook.capstone.petalk.dto.response.UpdateCommunityResponse;
import dankook.capstone.petalk.service.CommunityService;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService  communityService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    /**
     * 게시글 생성
     */
    @ApiOperation(value = "", notes = "새로운 게시글 생성")
    @PostMapping("/new")
    public ResponseData<CreateCommunityResponse> createCommunity(@RequestBody @Valid CreateCommunityRequest request, HttpServletRequest httpServletRequest){
        ResponseData<CreateCommunityResponse> responseData;
        CreateCommunityResponse createCommunityResponse;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member findMember = memberService.findOne(memberId);

            Community community = new Community(findMember, request.getTitle(), request.getContent());

            Long id = communityService.register(community);

            createCommunityResponse = new CreateCommunityResponse(id, findMember.getId(), community.getMember().getNickname(), community.getTitle());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createCommunityResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
            log.error("Can't find Member");
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.COMMUNITY_CREATION_FAIL, null);
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "", notes = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteCommunityDto> deleteCommunity(HttpServletRequest httpServletRequest, @PathVariable("id") Long id){
        ResponseData<DeleteCommunityDto> responseData = null;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Long communityMemberId = communityService.findOne(id).getMember().getId();

            communityService.checkMember(memberId, communityMemberId);

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,new DeleteCommunityDto(id));
        }catch(IllegalAccessException e){
            log.error("IllegalAccessException", e);
            responseData = new ResponseData<>(StatusCode.UNAUTHORIZED, ResponseMessage.FAIL_DELETE_COMMUNITY, null);
        }catch(NoSuchElementException e){
            log.error("NoSuchElementException", e);
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY,null);
        }

        return  responseData;
    }

    @Data
    @AllArgsConstructor
    static class DeleteCommunityDto{
        private Long id;
    }

    /**
     * 게시글 조회(페이징)
     */
    @ApiOperation(value = "", notes = "게시글 조회 with 페이징")
    @GetMapping("/page/{page}")
    public ResponseData<CommunityDtoList> getCommunityWithPaging(@PathVariable("page") int page){
        ResponseData<CommunityDtoList> responseData = null;
        CommunityDtoList communityDtoList = null;

        try{
            Slice<Community> slice = communityService.findAllBySlice(page);

            Slice<CommunityDto> dtoSlice = slice.map(s -> new CommunityDto(s));

            List<CommunityDto> content = dtoSlice.getContent();

            communityDtoList = new CommunityDtoList(content);

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, communityDtoList);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class CommunityDtoList{
        private List<CommunityDto> communityDtos;
    }

//    /**
//     * 게시글 조회(검색)
//     */
//    @ApiOperation(value = "", notes = "게시글 검색")
//    @GetMapping("/{id}")
//    public ResponseData<CommunityDto> getCommunity(@PathVariable("id") Long id){
//        ResponseData<CommunityDto> responseData = null;
//        CommunityDto communityDto = null;
//
//        try{
//            Community community = communityService.findOne(id);
//            communityDto = new CommunityDto(community);
//
//            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, communityDto);
//        }catch(NoSuchElementException e){
//            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, null);
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//        return responseData;
//    }

//    /**
//     * 게시글 내용 수정
//     */
//    @ApiOperation(value = "", notes = "게시글 수정")
//    @PutMapping("/{id}")
//    public ResponseData<UpdateCommunityResponse> updateCommunity(@PathVariable("id") Long id,
//                                                                 @RequestBody @Valid UpdateCommunityRequest request){
//        ResponseData<UpdateCommunityResponse> responseData = null;
//        UpdateCommunityResponse updateCommunityResponse;
//
//        try{
//            communityService.update(id,request.getContent());
//
//            Community community = communityService.findOne(id);
//
//            updateCommunityResponse = new UpdateCommunityResponse(id,community.getMember().getId(),community.getTitle(),community.getContent());
//
//            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,updateCommunityResponse);
//        }catch(NoSuchElementException e){
//            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, null);
//            log.error(e.getMessage());
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//
//        return responseData;
//    }
}
