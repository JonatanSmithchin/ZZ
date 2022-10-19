package com.example.gui.Client.service;


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


    public Socket checkUser(String userName, String password) {
        u.setUserName(userName);
        u.setPassword(password);
        boolean isSuccess = false;

        try {

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {

            } else {
                socket.close();
                return null;//登陆失败,则关闭Socket
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void onlineUserList(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_USER);
        message.setSender(u.getUserName());
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManagerOfClientThread.
                                    getClientThread(u.getUserName()).
                                    getSocket().getOutputStream());
            //通过UserName从管理线程的Map中得到该用户的线程对象关联的Socket的输出流
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}