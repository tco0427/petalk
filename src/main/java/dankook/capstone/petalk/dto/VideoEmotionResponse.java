package dankook.capstone.petalk.dto;

import dankook.capstone.petalk.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoEmotionResponse {
    private Long id;
    private Emotion emotion;
}