package dankook.capstone.petalk.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.File;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message="아이디는 필수 입니다.")
    private String id;
    @NotEmpty(message="비밀번호는 필수 입니다.")
    private String password;
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String email;

    private File profile;
}