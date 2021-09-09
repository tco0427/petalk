package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignUpResponse {
    private Long userId;
    private String token;
}
