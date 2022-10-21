package ZZ.service;

import ZZ.dao.UserDAO;
import ZZ.dao.impl.UserDaoImpl;
import ZZ.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class UserService {

    //private InputStream inputStream;
    //private SqlSessionFactory factory;
    //private SqlSession session;
    private UserDAO userDAO;

    public UserService() throws IOException {

        userDAO = new UserDaoImpl();
    }

    public boolean checkPassword(String userName,String password){
        User user = userDAO.getByUserName(userName);
        if(user.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
    public boolean isUserExists(String userName,String password){
        User user = userDAO.getByUserName(userName);
        if(user == null){
            User user1 = new User(Integer.toString((int)(Math.random()*100)),userName,password);
            System.out.println(user1);
            userDAO.updateUser(user1);
            return true;
        }else{
            return false;
        }
    }
    public void update(String userName,String password){
        userDAO.updateUser(new User("34",userName,password));
    }
}
