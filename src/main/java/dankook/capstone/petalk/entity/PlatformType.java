package dankook.capstone.petalk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlatformType {
    KAKAO("KAKAO"), NAVER("NAVER");

    private final String value;
}

