package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.VideoDto;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.PetService;
import dankook.capstone.petalk.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;

@RestController
@Slf4j
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final MemberService memberService;
    private final PetService petService;

    @ApiOperation(value = "", notes = "동영상 client로부터 받아오기")
    @PostMapping("/upload")
    public ResponseData<UploadVideoResponse> uploadVideo(@RequestBody @Valid UploadVideoRequest request){
        ResponseData<UploadVideoResponse> responseData;
        UploadVideoResponse uploadVideoResponse = null;

        try{
            Video video = new Video();

            Long memberId = request.getMemberId();
            Long petId = request.getPetId();
            File videoData = request.getVideo();

            video.setMember(memberService.findOne(memberId));
            video.setPet(petService.findOne(petId));
            video.setVideo(videoData);

            Long id = videoService.save(video);

            uploadVideoResponse = new UploadVideoResponse(id, video.getVideo(),video.getEmotion());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,uploadVideoResponse);
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.FAIL_UPLOAD_VIDEO, uploadVideoResponse);
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class UploadVideoResponse{
        private Long id;
        private File video;
        private Emotion emotion;
    }

    @Data
    static class UploadVideoRequest{
        private Long memberId;
        private Long petId;
        private File video;
    }

    @ApiOperation(value = "", notes = "동영상 client로 전송")
    @GetMapping("/{videoId}")
    public ResponseData<VideoDto> getVideo(@PathVariable("videoId") Long videoId){
        log.info("getVideoById : " + videoId);

        ResponseData<VideoDto> responseData = null;
        VideoDto videoDto = null;

        try{
            Video video = videoService.findOne(videoId);
            videoDto = new VideoDto(video.getId(),video.getVideo(),video.getEmotion());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,videoDto);

        }catch(IllegalArgumentException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_VIDEO,null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

}
