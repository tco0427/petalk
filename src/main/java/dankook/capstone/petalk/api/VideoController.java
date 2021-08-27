package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.request.UploadVideoRequest;
import dankook.capstone.petalk.dto.response.UploadVideoResponse;
import dankook.capstone.petalk.dto.response.VideoDto;
import dankook.capstone.petalk.dto.request.VideoEmotionRequest;
import dankook.capstone.petalk.dto.response.VideoEmotionResponse;
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
import java.util.NoSuchElementException;

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
        ResponseData<UploadVideoResponse> responseData = null;
        UploadVideoResponse uploadVideoResponse;

        try{
            Video video = new Video();

            String fileName = request.getFileName();
            Long duration = request.getDuration();
            Long size = request.getSize();
            String fileUri = request.getFileUri();

            video.setMember(memberService.findOne(request.getMemberId()));
            video.setPet(petService.findOne(request.getPetId()));

            video.setFileName(fileName);
            video.setDuration(duration);
            video.setSize(size);
            video.setFileUri(fileUri);

            Long id = videoService.save(video);

            uploadVideoResponse = new UploadVideoResponse(id, fileUri);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, uploadVideoResponse);
        }catch(NoSuchElementException e){
            log.error(e.getMessage());
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.FAIL_UPLOAD_VIDEO, null);
            log.error(e.getMessage());
        }
        return responseData;
    }

    @ApiOperation(value = "", notes = "동영상 client로 전송")
    @GetMapping("/{videoId}")
    public ResponseData<VideoDto> getVideo(@PathVariable("videoId") Long videoId){
        log.info("getVideoById : " + videoId);

        ResponseData<VideoDto> responseData = null;
        VideoDto videoDto;

        try{
            Video video = videoService.findOne(videoId);

            videoDto = new VideoDto(video.getId(),video.getFileName(),video.getSize(),video.getFileUri(),video.getEmotion());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,videoDto);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_VIDEO,null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @PostMapping("/emotion")
    public ResponseData<VideoEmotionResponse> getVideoEmotion(@RequestBody VideoEmotionRequest request){
        ResponseData<VideoEmotionResponse> responseData = null;
        VideoEmotionResponse videoEmotionResponse;

        try{
            Long id = request.getId();
            Emotion emotion = request.getEmotion();

            videoService.update(id, emotion);

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
}
