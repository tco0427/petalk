package dankook.capstone.petalk.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("member")
    private MemberDto memberDto;
}
