import ZZ.dao.UserDAO;
import ZZ.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    private InputStream inputStream;
    private SqlSessionFactory factory;
    private SqlSession session;
    private UserDAO userDAO;

    @Before
    public void init() throws Exception{
        inputStream = Resources.getResourceAsStream("SqlMApConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        session = factory.openSession();
        userDAO = session.getMapper(UserDAO.class);
    }

    @After
    public void destroy() throws IOException {
        session.commit();
        session.close();
        inputStream.close();
    }

    @Test
    public void testQuery(){
        User user = userDAO.getByUserName("master");
        System.out.println(user.getUserId());
    }

    @Test
    public void testUpdate(){
        userDAO.updateUser(new User("124","manager","234567"));
    }
}
