package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.comment.CommentDao;
import com.example.demo.src.comment.model.GetCommentRes;
import com.example.demo.src.comment.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class CommentProvider {
    private final CommentDao commentDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CommentProvider(CommentDao commentDao, JwtService jwtService) {
        this.commentDao = commentDao;
        this.jwtService = jwtService;
    }

    public List<GetCommentRes> getComments() throws BaseException{
        try{
            List<GetCommentRes> getCommentRes = commentDao.getComments();
            return getCommentRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCommentRes> getCommentsVideo(int videoIdx) throws BaseException{
        if(checkVideoIdx(videoIdx) != 1) {
            throw new BaseException(GET_COMMENT_VIDEO_IDX_NOT_EXISTS);
        }
        try{
            List<GetCommentRes> getCommentRes = commentDao.getCommentsVideo(videoIdx);
            return getCommentRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkVideoIdx(int videoIdx) throws BaseException {
        try{
            return commentDao.checkVideoIdx(videoIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetCommentRes> getCommentsPost(int postIdx) throws BaseException{
        if(checkPostIdx(postIdx) != 1) {
            throw new BaseException(GET_COMMENT_POST_IDX_NOT_EXISTS);
        }
        try{
            List<GetCommentRes> getCommentRes = commentDao.getCommentsPost(postIdx);
            return getCommentRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkPostIdx(int postIdx) throws BaseException {
        try{
            return commentDao.checkPostIdx(postIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkCommentIdx(int commentIdx) throws BaseException {
        try{
            return commentDao.checkCommentIdx(commentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
