package dankook.capstone.petalk.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommunitySearch {
    /**
     * 파라미터 조건이 있으면 where 문으로 검색 되어야 함
     */
    private String title; //게시글 제목
    private String nickname; //작성자 닉네임
}
