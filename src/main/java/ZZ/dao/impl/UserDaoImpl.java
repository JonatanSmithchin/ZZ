package ZZ.dao.impl;

import ZZ.dao.UserDAO;
import ZZ.domain.User;
import java.sql.*;
import java.util.ResourceBundle;

public class UserDaoImpl implements UserDAO {

    Driver driver;
    Connection conn;
    Statement stmt;

    {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("jdbcConfig");
            String driver = bundle.getString("jdbc.driver");
            String url = bundle.getString("jdbc.url");
            String user = bundle.getString("jdbc.username");
            String password = bundle.getString("jdbc.password");
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByUserName(String userName) {
        try {
            String sql = "select * from zz_user_tbl where userName = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,userName);
            ResultSet rs = pst.executeQuery();
            User user = null;
            if(rs.next()){
                user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean updateUser(User user) {

        try {
            String sql = "insert into zz_user_tbl values(?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUserId());
            pst.setString(2, user.getUserName());
            pst.setString(3,user.getPassword());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
