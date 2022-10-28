import ZZ.service.UserService;
import org.junit.Test;

import java.io.IOException;

public class ServiceTest {
    @Test
    public void testIsExist() throws IOException {
        UserService userService = new UserService();
//        userService.isUserExists("master","345678");
        userService.isUserExists("bbb","345678");
    }

    @Test
    public void testCheckUser() throws IOException {
        UserService userService = new UserService();
        System.out.println(userService.checkPassword("lx","123456"));

    }
}
