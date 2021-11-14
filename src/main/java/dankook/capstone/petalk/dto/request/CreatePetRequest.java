package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.entity.Gender;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePetRequest {
    
    private MultipartFile image;
    private String petName;
    private Gender gender;
    private String petType;
    private Integer petAge;
}
