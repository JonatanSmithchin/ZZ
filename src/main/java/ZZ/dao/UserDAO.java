package ZZ.dao;

import ZZ.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserDAO {
    @Select("select * from zz_user_tbl where userName = #{userName}")
    @Results(value = {
            @Result(column = "userId",property = "userId"),
            @Result(column = "userNAme",property = "userName"),
            @Result(column = "password",property = "password")
    }
    )
    User getByUserName(String userName);



    @Insert("insert into zz_user_tbl values(#{userId},#{userName},#{password})")
    Boolean updateUser(User user);
}
