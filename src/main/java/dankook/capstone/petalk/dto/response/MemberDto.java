package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.domain.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDto {
    private String userId;
    private String nickname;
    private String name;
    private String email;
    private List<Pet> petList;
}
