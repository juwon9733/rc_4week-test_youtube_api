package com.example.demo.src.comment;

import com.example.demo.src.comment.model.*;
import com.example.demo.src.video.model.GetVideoRes;
import com.example.demo.src.video.model.PatchVideoReq;
import com.example.demo.src.video.model.PatchVideoStatusReq;
import com.example.demo.src.video.model.PostVideoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCommentRes> getComments(){
        String getCommentsQuery = "select * from Comment";
        return this.jdbcTemplate.query(getCommentsQuery,
                (rs,rowNum) -> new GetCommentRes(
                        rs.getInt("Idx"),
                        rs.getInt("videoIdx"),
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("comment"))
        );
    }
    public List<GetCommentRes> getCommentsVideo(int videoIdx){
        String getCommentsVideoQuery = "select * from Comment where videoIdx = ? and videoIdx is not null";
        int getCommentsVideoParams = videoIdx;
        return this.jdbcTemplate.query(getCommentsVideoQuery,
                (rs,rowNum) -> new GetCommentRes(
                        rs.getInt("Idx"),
                        rs.getInt("videoIdx"),
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("comment")),
                getCommentsVideoParams
        );
    }
    public int checkVideoIdx(int videoIdx) {
        String checkUserIdxQuery = "select exists(select Idx from Video where Idx = ?)";
        int checkUserIdxParams = videoIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                checkUserIdxParams);
    }
    public int checkPostIdx(int postIdx) {
        String checkUserIdxQuery = "select exists(select Idx from Post where Idx = ?)";
        int checkUserIdxParams = postIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                checkUserIdxParams);
    }
    public int checkCommentIdx(int commentIdx) {
        String checkCommentIdxQuery = "select exists(select Idx from Comment where Idx = ?)";
        int checkComnetIdxParams = commentIdx;
        return this.jdbcTemplate.queryForObject(checkCommentIdxQuery,
                int.class,
                checkComnetIdxParams);
    }
    public List<GetCommentRes> getCommentsPost(int postIdx){
        String getCommentsPostQuery = "select * from Comment where postIdx = ? and postIdx is not null";
        int getCommentsPostParams = postIdx;
        return this.jdbcTemplate.query(getCommentsPostQuery,
                (rs,rowNum) -> new GetCommentRes(
                        rs.getInt("Idx"),
                        rs.getInt("videoIdx"),
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("comment")),
                getCommentsPostParams
        );
    }
    public int createComment(PostCommentReq postCommentReq){
        String createCommentQuery = "insert into Comment (videoIdx, postIdx, userIdx, comment) VALUES (?,?,?,?)";
        Object[] createCommentParams = new Object[]{postCommentReq.getVideoIdx(), postCommentReq.getPostIdx(),
                postCommentReq.getUserIdx(), postCommentReq.getComment()};
        this.jdbcTemplate.update(createCommentQuery, createCommentParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    public int modifyComment(PatchCommentReq patchCommentReq){
        String patchCommentQuery = "update Comment set comment = ? where Idx = ? ";
        Object[] patchCommentParams = new Object[]{patchCommentReq.getComment(), patchCommentReq.getCommentIdx()};

        return this.jdbcTemplate.update(patchCommentQuery,patchCommentParams);
    }
    public int modifyCommentStatus(PatchCommentStatusReq patchCommentStatusReq){
        String modifyCommentStatusQuery = "update Comment set status = ? where Idx = ? ";
        Object[] modifyCommentStatusParams = new Object[]{patchCommentStatusReq.getStatus(), patchCommentStatusReq.getCommentIdx()};
        return this.jdbcTemplate.update(modifyCommentStatusQuery,modifyCommentStatusParams);
    }
}
