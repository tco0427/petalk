package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Emotion;
import dankook.capstone.petalk.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VideoDto {
    private LocalDateTime lastModifiedDate;
    private Emotion emotion;

    public VideoDto(Video video){
        this.lastModifiedDate = video.getLastModifiedDate();
        this.emotion = video.getEmotion();
    }
}
