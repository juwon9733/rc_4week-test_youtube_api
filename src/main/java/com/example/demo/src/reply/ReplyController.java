package com.example.demo.src.reply;

import com.example.demo.src.comment.CommentProvider;
import com.example.demo.src.comment.CommentService;
import com.example.demo.src.comment.model.*;
import com.example.demo.src.reply.ReplyProvider;
import com.example.demo.src.reply.ReplyService;
import com.example.demo.src.reply.model.*;
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
@RequestMapping("/app/replys")
public class ReplyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReplyProvider replyProvider;
    @Autowired
    private final ReplyService replyService;
    @Autowired
    private final JwtService jwtService;

    public ReplyController(ReplyProvider replyProvider, ReplyService replyService, JwtService jwtService) {
        this.replyProvider = replyProvider;
        this.replyService = replyService;
        this.jwtService = jwtService;
    }
    /**
     * [22].
     * 특정 코멘트의 답글 조회 API
     * [GET] /replys/:commentIdx
     * @return BaseResponse<List<GetReplyRes>>
     */
    @ResponseBody
    @GetMapping("/{commentIdx}")
    public BaseResponse<List<GetReplyRes>> getReplys(@PathVariable("commentIdx") int commentIdx) {
        try{
            List<GetReplyRes> getReplyRes = replyProvider.getReplys(commentIdx);
            return new BaseResponse<>(getReplyRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [23].
     * 특정 코멘트의 답글 생성 API
     * [POST] /replys
     * @return BaseResponse<PostReplyRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostReplyRes> createReply(@RequestBody PostReplyReq postReplyReq) {
        if(postReplyReq.getUserIdx() == 0) {
            return new BaseResponse<>(POST_REPLY_EMPTY_USER_IDX);
        }
        if(postReplyReq.getCommentIdx() == 0) {
            return new BaseResponse<>(POST_REPLY_EMPTY_COMMENT_IDX);
        }
        if(postReplyReq.getReply() == null) {
            return new BaseResponse<>(POST_REPLY_EMPTY_REPLY);
        }
        try{
            PostReplyRes postReplyRes = replyService.createReply(postReplyReq);
            return new BaseResponse<>(postReplyRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [24].
     * 특정 답글 수정 API
     * [PATCH] /replys/:replyIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{replyIdx}")
    public BaseResponse<String> patchReply(@PathVariable("replyIdx") int replyIdx, @RequestBody Reply reply) {
        if(reply.getReply() == null) {
            return new BaseResponse<>(PATCH_REPLY_EMPTY_REPLY);
        }
        try {
            PatchReplyReq patchReplyReq = new PatchReplyReq(replyIdx, reply.getReply());
            replyService.patchReply(patchReplyReq);
            String result = "Reply patched";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [25].
     * 답글 상태변경(활성화, 비활성화) API
     * [PATCH] /replys/status/:replyIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/status/{replyIdx}")
    public BaseResponse<String> modifyReplyStatus(@PathVariable("replyIdx") int replyIdx, @RequestBody PatchReplyStatusReq patchReplyStatusReq) {
        if(patchReplyStatusReq.getStatus() == null) {
            return new BaseResponse<>(PATCH_REPLY_EMPTY_STATUS);
        }
        try {
            patchReplyStatusReq = new PatchReplyStatusReq(replyIdx, patchReplyStatusReq.getStatus());
            replyService.modifyReplyStatus(patchReplyStatusReq);
            String result = "Reply Status modified";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
