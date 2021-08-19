package dankook.capstone.petalk.dto;

import dankook.capstone.petalk.domain.Emotion;
import lombok.Data;

import java.io.File;

@Data
public class VideoDto {
    private Long id;
    private File video;
    private Emotion emotion;

    public VideoDto(Long id, File video, Emotion emotion) {
        this.id = id;
        this.video = video;
        this.emotion = emotion;
    }
}
