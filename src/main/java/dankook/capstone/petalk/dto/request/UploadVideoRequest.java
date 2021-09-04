package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class UploadVideoRequest {
    private Long memberId;
    private String fileName;
    private Long duration;
    private Long size;
    private String fileUri;
}
