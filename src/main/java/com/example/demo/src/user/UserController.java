package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * [1].
     * 전체 유저 조회 API
     * [GET] /users
     * + 이메일로 특정 유저 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try {
            if (Email == null) {
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [2].
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [3].
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if (postUserReq.getName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if (postUserReq.getBirth() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
        }
        if (postUserReq.getSex() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_SEX);
        }
        if (postUserReq.getPasswd() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWD);
        }
        if (postUserReq.getPhoneNumber() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
        }

        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [4].
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            if(postLoginReq.getEmail() == null) {
                return new BaseResponse<>(POST_LOGIN_EMPTY_EMAIL);
            }
            if(postLoginReq.getPasswd() == null) {
                return new BaseResponse<>(POST_LOGIN_EMPTY_PASSWD);
            }


            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [5].
     * 유저 정보 변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserInfo(@PathVariable("userIdx") int userIdx, @RequestBody User user){
        if (postUserReq.getName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if (postUserReq.getBirth() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
        }
        if (postUserReq.getSex() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_SEX);
        }
        if (postUserReq.getPasswd() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWD);
        }
        if (postUserReq.getPhoneNumber() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
        }
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (user.getName() == null) {
                return new BaseResponse<>(PATCH_USER_EMPTY_NAME);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getName());
            userService.modifyUserName(patchUserReq);

            String result = "User name patched";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [6].
     * 유저 구독 채널 조회 API
     * [GET] /users/subscribes/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("subscribes/{userIdx}")
    public BaseResponse<List<GetUserRes>> getUserSubs(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetUserRes> getUserRes = userProvider.getUserSubs(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [7].
     * 유저의 검색어 기록 조회 API
     * [GET] /users/history-searches/:userIdx
     * @return BaseResponse<GetUserSearchRes>
     */
    @ResponseBody
    @GetMapping("history-searches/{userIdx}")
    public BaseResponse<List<GetUserSearchRes>> getUserSearches(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetUserSearchRes> getUserSearchRes = userProvider.getUserSearches(userIdx);
            return new BaseResponse<>(getUserSearchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
