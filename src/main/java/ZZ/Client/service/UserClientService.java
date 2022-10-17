package ZZ.Client.service;


import ZZ.domain.Message;
import ZZ.domain.MessageType;
import ZZ.domain.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

    private User u = new User();
    Socket socket;

    public UserClientService() throws IOException {
        socket = new Socket(InetAddress.getByName("localhost"), 9999);
    }


    public boolean checkUser(String userName, String password) {
        u.setUserName(userName);
        u.setPassword(password);
        boolean isSuccess = false;

        try {

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                //创建新线程保证通信
                isSuccess = true;
            } else {
                socket.close();//登陆失败,则关闭Socket
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

}