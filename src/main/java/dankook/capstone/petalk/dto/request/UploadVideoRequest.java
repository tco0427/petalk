package dankook.capstone.petalk.dto.request;

import lombok.Data;

import java.io.File;

@Data
public class UploadVideoRequest {
    private Long memberId;
    private Long petId;
    private File video;
}
