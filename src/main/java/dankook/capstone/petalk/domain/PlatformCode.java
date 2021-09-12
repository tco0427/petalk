package dankook.capstone.petalk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlatformCode {

    GENERAL("01"), KAKAO("02");

    private final String value;
}
