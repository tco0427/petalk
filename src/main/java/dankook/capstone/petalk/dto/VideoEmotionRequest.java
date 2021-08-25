package dankook.capstone.petalk.dto;

import dankook.capstone.petalk.domain.Emotion;
import lombok.Data;

@Data
public class VideoEmotionRequest {
    private Long id;
    private Emotion emotion;
}
