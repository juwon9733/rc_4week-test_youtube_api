package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
        if(checkEmail(email) != 1) {
            throw new BaseException(GET_USER_EMAIL_NOT_EXISTS);
        }
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetUserRes getUser(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) != 1) {
            throw new BaseException(GET_USER_IDX_NOT_EXISTS);
        }
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserIdx(int userIdx) throws BaseException {
        try{
            return userDao.checkUserIdx(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        if(checkEmail(postLoginReq.getEmail()) != 1){
            throw new BaseException(FAILED_TO_LOGIN_BY_EMAIL);
        }
        // Request의 Email로 user를 특정하여, user 정보를 가져온다.
        User user = userDao.getUserToLogInByEmail(postLoginReq);
        String password;
        try {
            // 패스워드 복호화
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPasswd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        // Email로 가져온 user의 비밀번호를,
        // Request의 비밀번호와 비교한다.
        if(postLoginReq.getPasswd().equals(password)){
            int userIdx = userDao.getUserToLogInByEmail(postLoginReq).getIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN_BY_PASSWD);
        }
    }

    public List<GetUserRes> getUserSubs(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) != 1) {
            throw new BaseException(GET_USER_SUBS_IDX_NOT_EXISTS);
        }
        try {
            List<GetUserRes> getUserRes = userDao.getUserSubs(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetUserSearchRes> getUserSearches(int userIdx) throws BaseException{
        if(checkUserIdxInSearch(userIdx) != 1) {
            throw new BaseException(GET_SEARCHES_USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetUserSearchRes> getUserSearchRes = userDao.getUserSearches(userIdx);
            return getUserSearchRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkUserIdxInSearch(int userIdx) throws BaseException {
        try{
            return userDao.checkUserIdxInSearch(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
