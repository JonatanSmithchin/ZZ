package ZZ.service;

import ZZ.dao.UserDAO;
import ZZ.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class UserService {

    private InputStream inputStream;
    private SqlSessionFactory factory;
    private SqlSession session;
    private UserDAO userDAO;

    public UserService() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMApConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        session = factory.openSession();
        userDAO = session.getMapper(UserDAO.class);
    }

    public boolean checkPassword(String userName,String password){
        User user = userDAO.getByUserName(userName);
        if(user.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
