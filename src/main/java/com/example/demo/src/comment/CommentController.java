package com.example.demo.src.comment;

import com.example.demo.src.comment.CommentProvider;
import com.example.demo.src.comment.CommentService;
import com.example.demo.src.comment.model.*;
import com.example.demo.src.video.model.PatchVideoStatusReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;
@RestController
@RequestMapping("/app/comments")
public class CommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommentProvider commentProvider;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final JwtService jwtService;

    public CommentController(CommentProvider commentProvider, CommentService commentService, JwtService jwtService) {
        this.commentProvider = commentProvider;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    /**
     * [16].
     * 전체 코멘트 조회 API
     * [GET] /comments
     * @return BaseResponse<List<GetCommentRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetCommentRes>> getComments() {
        try{
            List<GetCommentRes> getCommentRes = commentProvider.getComments();
            return new BaseResponse<>(getCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [17].
     * 코멘트 생성 API
     * [POST] /comments
     * @return BaseResponse<PostCommentRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommentRes> createComment(@RequestBody PostCommentReq postCommentReq) {
        if(postCommentReq.getVideoIdx() != 0 && postCommentReq.getPostIdx() != 0) {
            return new BaseResponse<>(POST_COMMENT_DUPLICATED);
        }
        if(postCommentReq.getUserIdx() == 0) {
            return new BaseResponse<>(POST_COMMENT_EMPTY_USER_IDX);
        }
        if(postCommentReq.getComment() == null) {
            return new BaseResponse<>(POST_COMMENT_EMPTY_COMMENT);
        }
        try{
            PostCommentRes postCommentRes = commentService.createComment(postCommentReq);
            return new BaseResponse<>(postCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [18].
     * 특정 비디오 코멘트 조회 API
     * [GET] /comments/video:videoIdx
     * @return BaseResponse<List<GetCommentRes>>
     */
    @ResponseBody
    @GetMapping("/video/{videoIdx}")
    public BaseResponse<List<GetCommentRes>> getCommentsVideo(@PathVariable("videoIdx") int videoIdx) {
        try{
            List<GetCommentRes> getCommentRes = commentProvider.getCommentsVideo(videoIdx);
            return new BaseResponse<>(getCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [19].
     * 특정 게시물 코멘트 조회 API
     * [GET] /comments/post:postIdx
     * @return BaseResponse<List<GetCommentRes>>
     */

    @ResponseBody
    @GetMapping("/post/{postIdx}")
    public BaseResponse<List<GetCommentRes>> getCommentsPost(@PathVariable("postIdx") int postIdx) {
        try {
            List<GetCommentRes> getCommentRes = commentProvider.getCommentsPost(postIdx);
            return new BaseResponse<>(getCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [20].
     * 코멘트 수정 API
     * [PATCH] /comments/:commentIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{commentIdx}")
    public BaseResponse<String> modifyComment(@PathVariable("commentIdx") int commentIdx, @RequestBody PatchCommentReq patchCommentReq) {
        if(patchCommentReq.getComment() == null) {
            return new BaseResponse<>(PATCH_COMMENT_EMPTY_COMMENT);
        }
        try {
            patchCommentReq = new PatchCommentReq(commentIdx, patchCommentReq.getComment());
            commentService.modifyComment(patchCommentReq);
            String result = "comment modified";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [21].
     * 코멘트 상태변경(활성화, 비활성화) API
     * [PATCH] /comments/status/:commentIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/status/{commentIdx}")
    public BaseResponse<String> modifyCommentStatus(@PathVariable("commentIdx") int commentIdx, @RequestBody PatchCommentStatusReq patchCommentStatusReq) {
        if(patchCommentStatusReq.getStatus() == null) {
            return new BaseResponse<>(PATCH_COMMENT_EMPTY_STATUS);
        }
        try {
            patchCommentStatusReq = new PatchCommentStatusReq(commentIdx, patchCommentStatusReq.getStatus());
            commentService.modifyCommentStatus(patchCommentStatusReq);
            String result = "Comment Status modified";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
