package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoDto {
    private Long id;
    private Emotion emotion;
}
