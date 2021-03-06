package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePetResponse {
    private Long id;
    private String petName;
    private String profileUrl;
    private Gender gender;
    private String petType;
    private Integer petAge;
}
