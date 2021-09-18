package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoDto {
    private String fileUrl;
    private Emotion emotion;

    public VideoDto(Video video){
        this.fileUrl = video.getFileUrl();
        this.emotion = video.getEmotion();
    }
}
