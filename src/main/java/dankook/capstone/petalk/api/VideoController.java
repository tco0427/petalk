package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.entity.Emotion;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.Video;
import dankook.capstone.petalk.dto.response.UploadVideoResponse;
import dankook.capstone.petalk.dto.response.VideoDto;
import dankook.capstone.petalk.dto.request.VideoEmotionRequest;
import dankook.capstone.petalk.dto.response.VideoDtoList;
import dankook.capstone.petalk.dto.response.VideoEmotionResponse;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.S3Uploader;
import dankook.capstone.petalk.service.VideoService;
import dankook.capstone.petalk.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "", notes = "동영상 S3에 업로드하기")
    @PostMapping("/upload")
    public ResponseData<UploadVideoResponse> upload(@RequestParam("video") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        ResponseData<UploadVideoResponse> responseData = null;
        UploadVideoResponse uploadVideoResponse;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);

            String url = s3Uploader.upload(multipartFile, "static");

            Video video = new Video(member, url);

            Long videoId = videoService.save(video);

            uploadVideoResponse = new UploadVideoResponse(videoId, url);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, uploadVideoResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(IOException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.FAIL_UPLOAD_VIDEO, null);
            log.error(e.getMessage());
        }
        return responseData;
    }

    @ApiOperation(value = "", notes = "동영상 emotion 받아오기")
    @PostMapping("/emotion")
    public ResponseData<VideoEmotionResponse> getVideoEmotion(@RequestBody VideoEmotionRequest request){
        ResponseData<VideoEmotionResponse> responseData = null;
        VideoEmotionResponse videoEmotionResponse;

        try{
            Long id = request.getId();
            Emotion emotion = request.getEmotion();

            videoService.updateEmotion(id, emotion);

            videoEmotionResponse = new VideoEmotionResponse(id, emotion);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, videoEmotionResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_VIDEO, null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 동기적으로 video upload -> analysis -> client에게 보여주기에는 실력적인 부족함이 있으므로
     * 비동기적으로 처리하기로 계획 변경
     */
    @ApiOperation(value = "", notes = "나의 동영상 리스트 보기")
    @GetMapping("/videoList")
    public ResponseData<VideoDtoList> getVideoList(HttpServletRequest httpServletRequest){
        ResponseData<VideoDtoList> responseData = null;
        VideoDtoList videoDtoList;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);

            List<Video> videoList = videoService.findListByMember(member);

            List<VideoDto> videoDtos = new ArrayList<>();
            for (Video video : videoList) {
                VideoDto videoDto = new VideoDto(video);
                videoDtos.add(videoDto);
            }
            videoDtoList = new VideoDtoList(videoDtos);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, videoDtoList);
        }catch(NoSuchElementException e){
            log.error(e.getMessage());
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_VIDEO, null);
        }

        return responseData;
    }
}
