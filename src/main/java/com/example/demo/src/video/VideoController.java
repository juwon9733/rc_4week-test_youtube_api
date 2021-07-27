package com.example.demo.src.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.video.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/videos")
public class VideoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final VideoProvider videoProvider;
    @Autowired
    private final VideoService videoService;
    @Autowired
    private final JwtService jwtService;

    public VideoController(VideoProvider videoProvider, VideoService videoService, JwtService jwtService){
        this.videoProvider = videoProvider;
        this.videoService = videoService;
        this.jwtService = jwtService;
    }

    /**
     * [8].
     * 전체 비디오 조회 API
     * [GET] /videos
     * 비디오 제목 검색 조회 API
     * [GET] /videos? Title=
     * @return BaseResponse<List<GetVideoRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetVideoRes>> getVidoes(@RequestParam(required = false) String Title) {
        try{
            if(Title == null){
                List<GetVideoRes> getVideoRes = videoProvider.getVideos();
                return new BaseResponse<>(getVideoRes);
            }
            // Get Users
            List<GetVideoRes> getVideosRes = videoProvider.getVideosByTitle(Title);
            return new BaseResponse<>(getVideosRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [9].
     * 비디오 조회 API
     * [GET] /videos/:videoIdx
     * @return BaseResponse<GetVideoRes>
     */
    @ResponseBody
    @GetMapping("/{videoIdx}")
    public BaseResponse<GetVideoRes> getVideo(@PathVariable("videoIdx") int videoIdx) {
        try {
            GetVideoRes getVideoRes = videoProvider.getVideo(videoIdx);
            return new BaseResponse<>(getVideoRes);
        }catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [10].
     * 비디오 생성 API
     * [POST] /videos
     * @return BaseResponse<PostVideoRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostVideoRes> createUser(@RequestBody PostVideoReq postVideoReq) {
        if(postVideoReq.getUserIdx() == 0) {
            return new BaseResponse<>(POST_VIDEO_EMPTY_UESRIDX);
        }
        if(postVideoReq.getVideoUrl() == null) {
            return new BaseResponse<>(POST_VIDEO_EMPTY_VIDEOURL);
        }
        if(postVideoReq.getTitle() == null) {
            return new BaseResponse<>(POST_VIDEO_EMPTY_TITLE);
        }
        if(postVideoReq.getVideoTime() == null) {
            return new BaseResponse<>(POST_VIDEO_EMPTY_VIDEOTIME);
        }
        if(postVideoReq.getKind() == null) {
            return new BaseResponse<>(POST_VIDEO_EMPTY_KIND);
        }

        try{
            PostVideoRes postVideoRes = videoService.createVideo(postVideoReq);
            return new BaseResponse<>(postVideoRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [11].
     * 비디오 정보 수정
     * API
     * [PATCH] /videos/:videoIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{videoIdx}")
    public BaseResponse<String> modifyVideo(@PathVariable("videoIdx") int videoIdx, @RequestBody Video video) {
        if(video.getUserIdx() == 0) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_USERIDX);
        }
        if(video.getVideoUrl() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_VIDEOURL);
        }
        if(video.getTitle() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_TITLE);
        }
        if(video.getVideoExplain() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_EXPLAIN);
        }
        if(video.getVideoTime() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_VIDEOTIME);
        }
        if(video.getKind() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_KIND);
        }
        try {
            PatchVideoReq patchVideoReq = new PatchVideoReq(videoIdx, video.getUserIdx(), video.getVideoUrl(),
                    video.getTitle(), video.getVideoExplain(), video.getVideoTime(), video.getKind());
            videoService.modifyVideo(patchVideoReq);
            String result = "Video Modified";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [12].
     * 비디오 상태변경(활성화, 비활성화) API
     * API
     * [PATCH] /videos/status/:videoIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/status/{videoIdx}")
    public BaseResponse<String> modifyVideoStatus(@PathVariable("videoIdx") int videoIdx, @RequestBody PatchVideoStatusReq patchVideoStatusReq) {
        if(patchVideoStatusReq.getStatus() == null) {
            return new BaseResponse<>(PATCH_VIDEO_EMPTY_STATUS);
        }
        try {
            patchVideoStatusReq = new PatchVideoStatusReq(videoIdx, patchVideoStatusReq.getStatus());
            videoService.modifyVideoStatus(patchVideoStatusReq);
            String result = "Video Status modified";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [13].
     * 사용자 채널의 영상 조회 API
     * [GET] /videos/user-channel/:userIdx
     * @return BaseResponse<List<GetVideoRes>>
     */
    @ResponseBody
    @GetMapping("user-channel/{userIdx}")
    public BaseResponse<List<GetVideoRes>> getVideosByUser(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetVideoRes> getVideoRes = videoProvider.getVideosByUser(userIdx);
            return new BaseResponse<>(getVideoRes);
        }catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [14].
     * 사용자의 영상 기록 조회 API
     * [GET] /videos/history/:userIdx
     * @return BaseResponse<List<GetVideoRes>>
     */
    @ResponseBody
    @GetMapping("history/{userIdx}")
    public BaseResponse<List<GetVideoUserHistoryRes>> getVideosUserHistory(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetVideoUserHistoryRes> getVideoUserHistoryRes = videoProvider.getVideosUserHistory(userIdx);
            return new BaseResponse<>(getVideoUserHistoryRes);
        }catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [15].
     * 사용자의 나중에 볼 영상 조회 API
     * [GET] /videos/later-see/:userIdx
     * @return BaseResponse<List<GetVideoRes>>
     */
    @ResponseBody
    @GetMapping("later-see/{userIdx}")
    public BaseResponse<List<GetVideoRes>> getVideosLaterSee(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetVideoRes> getVideoRes = videoProvider.getVideosLaterSee(userIdx);
            return new BaseResponse<>(getVideoRes);
        }catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
