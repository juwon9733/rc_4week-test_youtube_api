package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.video.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class VideoService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VideoDao videoDao;
    private final VideoProvider videoProvider;
    private final JwtService jwtService;


    @Autowired
    public VideoService(VideoDao videoDao, VideoProvider videoProvider, JwtService jwtService) {
        this.videoDao = videoDao;
        this.videoProvider = videoProvider;
        this.jwtService = jwtService;

    }
    public PostVideoRes createVideo(PostVideoReq postVideoReq) throws BaseException {
        try{
            int videoIdx = videoDao.createVideo(postVideoReq);
            return new PostVideoRes(videoIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyVideoStatus(PatchVideoStatusReq patchVideoStatusReq) throws BaseException {
        if(videoProvider.checkVideoIdx(patchVideoStatusReq.getVideoIdx()) != 1) {
            throw new BaseException(PATCH_VIDEO_STATUS_IDX_NOT_EXISTS);
        }
        try{
            int result = videoDao.modifyVideoStatus(patchVideoStatusReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_VIDEO_STATUS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyVideo(PatchVideoReq patchVideoReq) throws BaseException {
        if(videoProvider.checkVideoIdx(patchVideoReq.getIdx()) != 1) {
            throw new BaseException(PATCH_VIDEO_INFO_IDX_NOT_EXISTS);
        }
        try{
            int result = videoDao.modifyVideo(patchVideoReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_VIDEO);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
