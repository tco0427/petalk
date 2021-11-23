package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.dto.response.CommunityDto;
import dankook.capstone.petalk.entity.Comment;
import dankook.capstone.petalk.entity.Community;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.dto.request.CreateCommentRequest;
import dankook.capstone.petalk.dto.request.UpdateCommentRequest;
import dankook.capstone.petalk.dto.response.CreateCommentResponse;
import dankook.capstone.petalk.dto.response.UpdateCommentResponse;
import dankook.capstone.petalk.service.CommentService;
import dankook.capstone.petalk.service.CommunityService;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final CommunityService communityService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "", notes = "comment 생성")
    @PostMapping("/new")
    public ResponseData<CreateCommentResponse> createComment(HttpServletRequest httpServletRequest, @RequestBody @Valid CreateCommentRequest request){
        ResponseData<CreateCommentResponse> responseData = null;
        CreateCommentResponse createCommentResponse;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);
            Community community = communityService.findOne(request.getCommunityId());
            String content = request.getContent();

            Comment comment = new Comment(member, content, community);

            Long id = commentService.register(comment);

            createCommentResponse = new CreateCommentResponse(id, community.getId(), community.getMember().getNickname(), content);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createCommentResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.UNAUTHORIZED, ResponseMessage.COMMENT_CREATION_FAIL, null);
            log.error(e.getMessage(), e);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }

        return responseData;
    }

    @ApiOperation(value = "", notes = "댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteCommentDto> deleteComment(HttpServletRequest httpServletRequest, @PathVariable("id") Long id){
        ResponseData<DeleteCommentDto> responseData = null;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Long commentMemberId = commentService.findOne(id).getMember().getId();

            commentService.checkMember(memberId, commentMemberId);

            commentService.deleteById(id);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, new DeleteCommentDto(id));
        }catch(NoSuchElementException e) {
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT, null);
        }catch(IllegalAccessException e) {
            log.error("IllegalAccessException", e);
            responseData = new ResponseData<>(StatusCode.UNAUTHORIZED, ResponseMessage.FAIL_DELETE_COMMENT, null);
        }

        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class DeleteCommentDto{
        private Long id;
    }

    @ApiOperation(value = "", notes = "댓글 리스트 조회")
    @GetMapping("/{communityId}")
    public ResponseData<CommunityDto> getCommentList(@PathVariable("communityId") Long id) {
        ResponseData<CommunityDto> responseData = null;
        CommunityDto communityDto;

        try{
            Community community = communityService.findOne(id);

            communityDto = new CommunityDto(community);

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, communityDto);
        }catch(NoSuchElementException e) {
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT, null);
        }

        return responseData;
    }


//    @ApiOperation(value = "", notes = "댓글 수정")
//    @PutMapping("/{id}")
//    public ResponseData<UpdateCommentResponse> updateComment(@PathVariable("id") Long id,
//                                                             @RequestBody @Valid UpdateCommentRequest request){
//        ResponseData<UpdateCommentResponse> responseData = null;
//        UpdateCommentResponse updateCommentResponse;
//
//        try{
//            commentService.update(id, request.getContent());
//
//            Comment comment = commentService.findOne(id);
//
//            updateCommentResponse = new UpdateCommentResponse(id, comment.getCommunity().getId(), comment.getContent());
//            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, updateCommentResponse);
//        }catch(NoSuchElementException e){
//            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT, null);
//            log.error(e.getMessage());
//        }catch(Exception e){
//            e.getMessage();
//        }
//
//        return responseData;
//    }
}
