package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // [3]. [POST] /users
    POST_USERS_EMPTY_NAME(false, 2015, "이름을 입력해주세요"),
    POST_USERS_EMPTY_BIRTH(false, 2016, "출생일을 입력해주세요"),
    POST_USERS_EMPTY_SEX(false, 2017, "성별을 입력해주세요"),
    POST_USERS_EMPTY_PASSWD(false, 2018, "비밀번호를 입력해주세요"),
    POST_USERS_EMPTY_EMAIL(false, 2019, "이메일을 입력해주세요."),
    POST_USERS_EMPTY_PHONENUMBER(false, 2020, "핸드폰 번호를 입력해주세요"),
    POST_USERS_INVALID_EMAIL(false, 2021, "이메일 형식을 확인해주세요."),

    // [4]. [POST] /users/logIn
    POST_LOGIN_EMPTY_EMAIL(false, 2022, "이메일을 입력해주세요"),
    POST_LOGIN_EMPTY_PASSWD(false, 2023, "비밀번호를 입력해주세요"),

    // [5]. [PATCH] /users/:userIdx
    PATCH_USER_EMPTY_NAME(false, 2024, "이름을 입력해주세요"),

    // [10]. [POST] /videos
    POST_VIDEO_EMPTY_UESRIDX(false, 2025, "유저 인덱스를 입력해주세요"),
    POST_VIDEO_EMPTY_VIDEOURL(false, 2026, "비디오 주소를 입력해주세요"),
    POST_VIDEO_EMPTY_TITLE(false, 2027, "비디오 제목을 입력해주세요"),
    POST_VIDEO_EMPTY_VIDEOTIME(false, 2028, "비디오 시간을 입력해주세요"),
    POST_VIDEO_EMPTY_KIND(false, 2029, "비디오 종류를 입력해주세요"),

    // [11]. [PATCH] /videos/:videoIdx
    PATCH_VIDEO_EMPTY_USERIDX(false, 2030, "유저 인덱스를 입력해주세요"),
    PATCH_VIDEO_EMPTY_VIDEOURL(false, 2031, "비디오 주소를 입력해주세요"),
    PATCH_VIDEO_EMPTY_TITLE(false, 2032, "비디오 제목을 입력해주세요"),
    PATCH_VIDEO_EMPTY_EXPLAIN(false, 2033, "비디오 설명을 입력해주세요"),
    PATCH_VIDEO_EMPTY_VIDEOTIME(false, 2034, "비디오 시간을 입력해주세요"),
    PATCH_VIDEO_EMPTY_KIND(false, 2035, "비디오 종류을 입력해주세요"),

    // [12]. [PATCH] /videos/status/:videoIdx
    PATCH_VIDEO_EMPTY_STATUS(false, 2036, "비디오 상태를 입력해주세요"),

    // [17]. [POST] /comments
    POST_COMMENT_DUPLICATED(false, 2037, "비디오 댓글, 게시글 댓글 둘중 하나만 선택해주세요"),
    POST_COMMENT_EMPTY_USER_IDX(false, 2038, "유저 인덱스를 입력해주세요"),
    POST_COMMENT_EMPTY_COMMENT(false, 2049, "코멘트를 입력해주세요"),

    // [20]. [PATCH] /comments/:commentIdx
    PATCH_COMMENT_EMPTY_COMMENT(false, 2050, "코멘트를 입력해주세요"),

    // [21]. [PATCH] /comments/status/:commentIdx
    PATCH_COMMENT_EMPTY_STATUS(false, 2051, "코멘트의 상태를 입력해주세요"),

    // [23]. [POST] /replys
    POST_REPLY_EMPTY_USER_IDX(false, 2052, "유저 인덱스를 입력해주세요"),
    POST_REPLY_EMPTY_COMMENT_IDX(false, 2053, "코멘트 인덱스를 입력해주세요"),
    POST_REPLY_EMPTY_REPLY(false, 2054, "답글을 입력해주세요"),

    // [24]. [PATCH] /replys/:replyIdx
    PATCH_REPLY_EMPTY_REPLY(false, 2055, "수정 답글을 입력해주세요"),

    // [25]. [PATCH] /replys/status/:replyIdx
    PATCH_REPLY_EMPTY_STATUS(false, 2056, "답글 상태를 입력해주세요"),

    /**
     *3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [1]. [GET] /users?Email=
    GET_USER_EMAIL_NOT_EXISTS(false, 3001, "존재하지 않는 이메일입니다."),

    // [2]. [GET] /users/:userIdx
    GET_USER_IDX_NOT_EXISTS(false, 3002, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [3]. [POST] /users
    POST_DUPLICATED_EMAIL(false, 3015, "중복된 이메일입니다."),

    // [4]. [POST] /users/logIn
    FAILED_TO_LOGIN_BY_EMAIL(false, 3020, "존재하지 않는 이메일입니다."),
    FAILED_TO_LOGIN_BY_PASSWD(false,3021,"비밀번호가 틀렸습니다."),
    USER_IDX_IS_NOT_EXISTS(false, 3022, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [6]. [GET] /users/subscribes/:userIdx
    GET_USER_SUBS_IDX_NOT_EXISTS(false, 3023, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [7]. [GET] /users/history-searches/:userIdx
    GET_SEARCHES_USER_IDX_NOT_EXISTS(false, 3024, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [9]. [GET] /videos/:videoIdx
    GET_VIDEO_IDX_NOT_EXISTS(false, 3025, "해당 비디오 인덱스 값이 존재하지 않습니다."),

    // [11]. [PATCH] /videos/:videoIdx
    PATCH_VIDEO_STATUS_IDX_NOT_EXISTS(false, 3026, "해당 비디오 인덱스 값이 존재하지 않습니다."),

    // [12]. [PATCH] /videos/:videoIdx
    PATCH_VIDEO_INFO_IDX_NOT_EXISTS(false, 3027, "해당 비디오 인덱스 값이 존재하지 않습니다."),

    // [13]. [GET] /videos/channel/:userIdx
    GET_VIDEO_USER_IDX_NOT_EXISTS(false, 3028, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [14]. [GET] /videos/history/:userIdx
    GET_VIDEO_HISTORY_USER_IDX_NOT_EXISTS(false, 3029, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [15]. [GET] /videos/later-see/:userIdx
    GET_LATER_VIDEO_USER_IDX_NOT_EXISTS(false, 3030, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [18]. [GET] /comments/video/:videoIdx
    GET_COMMENT_VIDEO_IDX_NOT_EXISTS(false, 3031, "해당 비디오 인덱스 값이 존재하지 않습니다."),

    // [19]. [GET] /comments/post:/postIdx
    GET_COMMENT_POST_IDX_NOT_EXISTS(false, 3032, "해당 게시물 인덱스 값이 존재하지 않습니다."),

    // [20]. [PATCH] /comments/:commentIdx
    PATCH_COMMENT_INFO_IDX_NOT_EXISTS(false, 3033, "해당 코멘트 인덱스 값이 존재하지 않습니다."),

    // [21]. [PATCH] /comments/status/:commentIdx
    PATCH_COMMENT_STATUS_IDX_NOT_EXISTS(false, 3034, "해당 코멘트 인덱스 값이 존재하지 않습니다."),

    // [22]. [GET] /replys/:commnetIdx
    GET_REPLY_COMMENT_IDX_NOT_EXISTS(false, 3035, "해당 코멘트 인덱스 값이 존재하지 않습니다."),

    // [23]. [POST] /replys
    POST_REPLY_COMMENT_IDX_NOT_EXISTS(false, 3036, "해당 코멘트 인덱스 값이 존재하지 않습니다."),
    POST_REPLY_USER_IDX_NOT_EXISTS(false, 3037, "해당 유저 인덱스 값이 존재하지 않습니다."),

    // [24]. [PATCH] /replys/:replyIdx
    PATCH_REPLY_IDX_NOT_EXISTS(false, 3038, "해당 답글 인덱스 값이 존재하지 않습니다."),

    // [25]. [PATCH] /replys/status/:replyIdx
    PATCH_REPLY_STATUS_IDX_NOT_EXISTS(false, 3039, "해당 답글 인덱스가 존재하지 않습니다."),
    /**
     * 4000 : Database, Server 오류
     */
    // Common
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    // [3]. [POST] /users
    PASSWORD_ENCRYPTION_ERROR(false, 4010, "비밀번호 암호화에 실패하였습니다."),

    // [4]. [POST] /users/logIn
    PASSWORD_DECRYPTION_ERROR(false, 4015, "비밀번호 복호화에 실패하였습니다."),

    // [5]. [PATCH] /users/:userIdx
    MODIFY_FAIL_USERNAME(false,4020,"유저네임 수정 실패"),

    // [11]. [PATCH] /videos/:videoIdx
    MODIFY_FAIL_VIDEO(false, 4021, "비디오 정보 수정 실패"),

    // [12]. [PATCH] /videos/status/:videoIdx
    MODIFY_FAIL_VIDEO_STATUS(false, 4022, "비디오 상태 수정 실패"),

    // [20]. [PATCH] /comments/:commentIDx
    MODIFY_FAIL_COMMENT(false, 4023, "코멘트 수정 실패"),

    // [21]. [PATCH] /comments/status/:commentIdx
    MODIFY_FAIL_COMMENT_STATUS(false, 4024, "코멘트 상태 수정 실패"),

    // [24]. [PATCH] /replys/:rpelyIdx
    MODIFY_FAIL_REPLY(false, 4025, "답글 수정 실패"),

    // [25]. [PATCH] /replys/status/:replyIdx
    MODIFY_FAIL_REPLY_STATUS(false, 4026, "답글 상태 수정 실패");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
