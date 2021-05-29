package dankook.capstone.petalk.controller;


import dankook.capstone.petalk.domain.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PetForm {
    @NotEmpty(message = "펫 이름은 입력해주세요ㅜㅜ")
    private String petName;

    private Gender gender;

    private String petType;

    private Integer petAge;
}
