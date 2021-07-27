package com.example.demo.src.reply;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.reply.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReplyProvider {

    private final ReplyDao replyDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReplyProvider(ReplyDao replyDao, JwtService jwtService) {
        this.replyDao = replyDao;
        this.jwtService = jwtService;
    }
    public List<GetReplyRes> getReplys(int commentIdx) throws BaseException{
        if(checkCommentIdx(commentIdx) != 1) {
            throw new BaseException(GET_REPLY_COMMENT_IDX_NOT_EXISTS);
        }
        try{
            List<GetReplyRes> getReplyRes = replyDao.getReplys(commentIdx);
            return getReplyRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkUserIdx(int userIdx) throws BaseException {
        try{
            return replyDao.checkUserIdx(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkCommentIdx(int commentIdx) throws BaseException {
        try{
            return replyDao.checkCommentIdx(commentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkReplyIdx(int replyIdx) throws BaseException {
        try{
            return replyDao.checkReplyIdx(replyIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
