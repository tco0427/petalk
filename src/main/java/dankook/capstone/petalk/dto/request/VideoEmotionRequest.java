package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.entity.Emotion;
import lombok.Data;

@Data
public class VideoEmotionRequest {
    private Long id;
    private Emotion emotion;
}
