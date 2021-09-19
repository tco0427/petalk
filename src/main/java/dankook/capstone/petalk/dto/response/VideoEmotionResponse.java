package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoEmotionResponse {
    private Long id;
    private Emotion emotion;
}
