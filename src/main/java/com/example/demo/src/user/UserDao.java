package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("sex"),
                        rs.getString("passwd"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select * from User where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("sex"),
                        rs.getString("passwd"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")),
                getUsersByEmailParams);
        // RowMapper를 이용한, 람다식
    }

    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select * from User where Idx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                // query : 리스트를 받아올 때,
                // queryForObject : 한가지를 받아올 때,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("sex"),
                        rs.getString("passwd"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")),
                getUserParams);
        // RowMapper를 이용한, 람다식
    }


    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (imageUrl, name, birth, sex, passwd, email, phoneNumber) VALUES (?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getImageUrl(), postUserReq.getName(), postUserReq.getBirth(),
                postUserReq.getSex(), postUserReq.getPasswd(), postUserReq.getEmail(), postUserReq.getPhoneNumber()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInserIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public int checkUserIdx(int userIdx) {
        String checkUserIdxQuery = "select exists(select Idx from User where Idx = ?)";
        int checkUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                checkUserIdxParams);
    }
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }


    public User getUserToLogInByEmail(PostLoginReq postLoginReq) {
        String getPwdQuery = "select * from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();     //PostLoginReq 클래스에, @Getter가 있어서 getEmail()가 가능한 것이다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                // query : 리스트를 받아올 때,
                // queryForObject : 한가지를 받아올 때,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("sex"),
                        rs.getString("passwd"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")),
                getPwdParams);
    }
    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set name = ? where Idx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }
    public List<GetUserRes> getUserSubs(int userIdx) {
        String getUsersQuery = "select * " +
                "from User " +
                "inner join(select subscribeUserIdx" +
                "                    from SubscribeChannel" +
                "                    where userIdx = ?) UserSub on User.Idx = UserSub.subscribeUserIdx";
        int getUserSubsParams = userIdx;
        return this.jdbcTemplate.query(getUsersQuery,
                // query : 리스트를 받아올 때,
                // queryForObject : 한가지를 받아올 때,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("sex"),
                        rs.getString("passwd"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")),
                getUserSubsParams);
    }
    public List<GetUserSearchRes> getUserSearches(int userIdx) {
        String getUserSearchesQuery = "select * from HistorySearch where userIdx = ? order by updatedAt desc";
        int getUserSearchesParams = userIdx;
        return this.jdbcTemplate.query(getUserSearchesQuery,
                (rs, rowNum) -> new GetUserSearchRes(
                        rs.getInt("userIdx"),
                        rs.getString("text")),
                getUserSearchesParams);
    }
    public int checkUserIdxInSearch(int userIdx) {
        String checkUserIdxInSearchQuery = "select exists(select userIdx from HistorySearch where userIdx = ?)";
        int checkUserIdxInSearchParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxInSearchQuery,
                int.class,
                checkUserIdxInSearchParams);
    }
}
