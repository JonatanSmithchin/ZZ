package ZZ.Server;

import ZZ.Server.Service.ManagerOfServerThread;
import ZZ.Server.Service.ServerConnectClientThread;
import ZZ.domain.Message;
import ZZ.domain.MessageType;
import ZZ.domain.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.logIn();
    }
    private ServerSocket ss = null;

    public Server(){
        System.out.println("服务器监听9999端口");
        try {
            ss = new ServerSocket(9999);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logIn(){
        while(true){
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;
            try {
                Socket socket = ss.accept();
                 ois = new ObjectInputStream(socket.getInputStream());
                 oos = new ObjectOutputStream(socket.getOutputStream());
                User user = (User) ois.readObject();
                Message message = new Message();
                if(user.getPassword().equals("123456")){//暂时先让用户的密码默认为123456，以后改为用数据库存储用户信息
                    System.out.println("登陆成功！");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, user.getUserName());
                    scct.start();
                    ManagerOfServerThread.addServerThread(scct.getUserName(), scct);
                }else{
                    System.out.println("登录失败！");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(message);
                    socket.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
