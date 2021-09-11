package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.domain.Gender;
import lombok.Data;

@Data
public class UpdatePetRequest {
    private String petName;
    private Gender gender;
    private String petType;
    private Integer petAge;
}
