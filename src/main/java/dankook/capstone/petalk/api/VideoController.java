package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.PetService;
import dankook.capstone.petalk.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

            Emotion emotion = null; // 머신러닝 이용하여 Emotion 구해오는 작업 필요

            video.setMember(memberService.findOne(memberId));
            video.setPet(petService.findOne(petId));
            video.setVideo(request.getVideo());
            video.setEmotion(emotion);

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
}
