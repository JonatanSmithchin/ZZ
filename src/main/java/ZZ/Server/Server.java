package ZZ.Server;

import ZZ.Server.Service.ManagerOfServerThread;
import ZZ.Server.Service.ServerConnectClientThread;
import ZZ.domain.Message;
import ZZ.domain.MessageType;
import ZZ.domain.User;
import ZZ.service.UserService;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.logIn();
    }
    private ServerSocket ss = null;

    UserService userService = null;

    public Server(){
        System.out.println("服务器监听9999端口");
        try {
            ss = new ServerSocket(9999);
            userService = new UserService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logIn() throws IOException {
        while(true){

            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;

            try {

                Socket socket = ss.accept();

                 ois = new ObjectInputStream(socket.getInputStream());
                 oos = new ObjectOutputStream(socket.getOutputStream());

                User user = (User) ois.readObject();

                Message message = new Message();
                if(checkUser(user)){
                    ServerConnectClientThread serverService = new ServerConnectClientThread(socket, user.getUserName());
                    serverService.start();
                    ManagerOfServerThread.addServerThread(user.getUserName(),serverService);
                    System.out.println("登陆成功！"+ ManagerOfServerThread.getServerThread(user.getUserName()));
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                }else{
                    System.out.println("登录失败！");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(message);
                    socket.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally{

            }

        }
    }

    private Boolean checkUser(User user){
        String password = user.getPassword();
        String userName = user.getUserName();

        if(userService.checkPassword(userName,password)){
            return true;
        }
        return false;
    }
}
