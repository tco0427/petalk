package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.Pet;
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

    public MemberDto(Member member){
        this.userId = member.getUserId();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
        this.petList = member.getPetList();
    }
}
