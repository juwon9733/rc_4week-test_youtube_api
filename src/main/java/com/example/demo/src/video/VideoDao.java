package com.example.demo.src.video;

import com.example.demo.src.video.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class VideoDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetVideoRes> getVideos(){
        String getVideosQuery = "select * from Video";
        return this.jdbcTemplate.query(getVideosQuery,
                (rs,rowNum) -> new GetVideoRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("videoTime"),
                        rs.getString("kind"))
        );
    }
    public List<GetVideoRes> getVideosByTitle(String title) {
        String getVideosByTitleQuery = "select * from Video where title like ?";
        String getvideosbyTitleParams = "%"+title+"%";
        return this.jdbcTemplate.query(getVideosByTitleQuery,
                (rs,rowNum) -> new GetVideoRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("videoTime"),
                        rs.getString("kind")),
                getvideosbyTitleParams
        );
    }

    public GetVideoRes getVideo(int videoIdx){
        String getVideoQuery = "select * from Video where Idx = ?";
        int getVideoParams = videoIdx;
        return this.jdbcTemplate.queryForObject(getVideoQuery,
                (rs,rowNum) -> new GetVideoRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("videoTime"),
                        rs.getString("kind")),
                getVideoParams
        );
    }

    public int createVideo(PostVideoReq postVideoReq){
        String createVideoQuery = "insert into Video (userIdx, videoUrl, title, videoExplain, videoTime, kind) VALUES (?,?,?,?,?,?)";
        Object[] createVideoParams = new Object[]{postVideoReq.getUserIdx(), postVideoReq.getVideoUrl(),
                postVideoReq.getTitle(), postVideoReq.getVideoExplain(), postVideoReq.getVideoTime(), postVideoReq.getKind()};
        this.jdbcTemplate.update(createVideoQuery, createVideoParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
    public int modifyVideoStatus(PatchVideoStatusReq patchVideoStatusReq){
        String modifyVideoStatusQuery = "update Video set status = ? where Idx = ? ";
        Object[] modifyVideoStatusParams = new Object[]{patchVideoStatusReq.getStatus(), patchVideoStatusReq.getVideoIdx()};
        return this.jdbcTemplate.update(modifyVideoStatusQuery,modifyVideoStatusParams);
    }
    public int modifyVideo(PatchVideoReq patchVideoReq){
        String modifyVideoQuery = "update Video set userIdx = ?, videoUrl = ?, title = ?, videoExplain = ?," +
                "videoTime = ?, kind = ? where Idx = ? ";
        Object[] modifyVideoParams = new Object[]{patchVideoReq.getUserIdx(), patchVideoReq.getVideoUrl(),
                patchVideoReq.getTitle(), patchVideoReq.getVideoExplain(), patchVideoReq.getVideoTime(),
                patchVideoReq.getKind(), patchVideoReq.getIdx()};
        return this.jdbcTemplate.update(modifyVideoQuery,modifyVideoParams);
    }
    public int checkUserIdx(int userIdx) {
        String checkUserIdxQuery = "select exists(select Idx from User where Idx = ?)";
        int checkUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                checkUserIdxParams);
    }
    public int checkVideoIdx(int videoIdx) {
        String checkUserIdxQuery = "select exists(select Idx from Video where Idx = ?)";
        int checkUserIdxParams = videoIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                checkUserIdxParams);
    }
    public List<GetVideoRes> getVideosByUser(int userIdx) {
        String getVideosByUsereQuery = "select * from Video where userIdx = ?";
        int getVideosbyUserParams = userIdx;
        return this.jdbcTemplate.query(getVideosByUsereQuery,
                (rs,rowNum) -> new GetVideoRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("videoTime"),
                        rs.getString("kind")),
                getVideosbyUserParams
        );
    }
    public List<GetVideoUserHistoryRes> getVideosUserHistory(int userIdx) {
        String getVideosUserHistoryQuery = "select *" +         // select Idx, userIdx, videoUrl, title, videoExplain, lastPlayTime, kind
                "from Video" +
                "         inner join(select videoIdx, max(playTime) as lastPlayTime" +
                "                    from HistoryVideo" +
                "                    where userIdx = ? " +
                "                    group by videoIdx" +
                ") UserOneHistoryVideo on Video.Idx = UserOneHistoryVideo.videoIdx";
        int getVideosUserHistoryParams = userIdx;
        return this.jdbcTemplate.query(getVideosUserHistoryQuery,
                (rs,rowNum) -> new GetVideoUserHistoryRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("lastPlayTime"),
                        rs.getString("kind")),
                getVideosUserHistoryParams);
    }
    public List<GetVideoRes> getVideosLaterSee(int userIdx) {
        String getVideosLaterSeeQuery = "select *\n" +
                "from Video\n" +
                "inner join(select videoIdx\n" +
                "    from LaterSeeVideo\n" +
                "    where userIdx = ?) UserOneLaterVideo on Video.Idx = UserOneLaterVideo.videoIdx";
        int getVideosLaterSeeParams = userIdx;
        return this.jdbcTemplate.query(getVideosLaterSeeQuery,
                (rs,rowNum) -> new GetVideoRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("videoUrl"),
                        rs.getString("title"),
                        rs.getString("videoExplain"),
                        rs.getString("videoTime"),
                        rs.getString("kind")),
                getVideosLaterSeeParams
        );
    }

}
