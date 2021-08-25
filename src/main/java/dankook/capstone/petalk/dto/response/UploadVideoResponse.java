package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class UploadVideoResponse {
    private Long id;
    private File video;
    private Emotion emotion;
}
