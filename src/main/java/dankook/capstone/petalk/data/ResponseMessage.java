package dankook.capstone.petalk.data;

public class ResponseMessage {
    public static final String SUCCESS = "성공";
    public static final String MEMBER_CREATION_FAIL = "회원 생성 실패";
    public static final String PET_CREATION_FAIL = "펫 정보 생성 실패";
    public static final String COMMUNITY_CREATION_FAIL = "게시글 생성 실패";
    public static final String NOT_FOUND_USER = "회원 정보 조회 실패";
    public static final String NOT_FOUND_PET = "펫 정보 조회 실패";
    public static final String NOT_FOUND_COMMUNITY = "게시글 정보 조회 실패";
    public static final String FAIL_UPLOAD_VIDEO = "비디오 정보 업로드 실패";
    public static final String NOT_FOUND_VIDEO = "비디오 정보 조회 실패";
    public static final String COMMENT_CREATION_FAIL = "댓글 생성 실패";
    public static final String NOT_FOUND_COMMENT = "댓글 조회 실패";

    public static final String FAILED_TO_SAVE_VOTE = "투표 생성 실패";
    public static final String EXPIRED_TOKEN = "유효기간 만료된 토큰";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰 정보";
    public static final String INVALID_HEADER = "잘못된 헤더 정보";
}
