package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class SignUpResponse {
    private Long userId;
    private String token;
}
