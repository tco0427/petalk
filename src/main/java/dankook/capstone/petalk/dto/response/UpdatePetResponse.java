package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePetResponse {
    private Long id;
    private String petName;
    private Gender gender;
    private String petType;
    private Integer petAge;
}
