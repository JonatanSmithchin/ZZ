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


    public boolean checkUser(String userName, String password) {
        u.setUserName(userName);
        u.setPassword(password);
        boolean b = false;

        try {
            socket = new Socket(InetAddress.getByName("localhost"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                //创建新线程保证通信
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                ManagerOfClientThread.addClientThread(userName,ccst);
                b = true;
            } else {
                socket.close();//登陆失败,则关闭Socket
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
    public void onlineUserList(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_USER);
        message.setSender(u.getUserName());
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManagerOfClientThread.
                            getClientThread(u.getUserName()).
                            getSocket().getOutputStream());
            //通过UserId从管理线程的Map中得到该用户的线程对象关联的Socket的输出流
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exit(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserName());
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManagerOfClientThread.
                                    getClientThread(u.getUserName()).
                                    getSocket().getOutputStream());
            System.out.println("退出登录。。。");
            oos.writeObject(message);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}