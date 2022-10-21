package ZZ.Server.Service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userName;

    public ServerConnectClientThread(Socket socket, String userName){
        this.socket=socket;
        this.userName=userName;
    }
    @Override
    public void run() {
        label:
        while(true){
            try {
                System.out.println("和"+userName+"保持通信,读取数据");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                String messageType = message.getMessageType();
                //判断Message类型,进行相应处理
                switch (messageType) {
                    case MessageType.MESSAGE_GET_ONLINE_USER: //查询在线用户
                        ManagerOfServerThread.getOnlineUsers(userName, message.getSender());
                        break;
                    case MessageType.MESSAGE_COMN_MSG: //私聊
                        ManagerOfServerThread.msg(message);
                        break;
                    case MessageType.MESSAGE_CLIENT_EXIT: //退出
                        System.out.println(userName+"wait");
                        ManagerOfServerThread.removeServerThread(message, userName, socket);
                        System.out.println(userName+"logout");
                        break label;
                    case MessageType.MESSAGE_GROUP_MSG: //群发
                        ManagerOfServerThread.groupMsg(message);
                        break;
                    case MessageType.MESSAGE_FILE_MSE: //传输文件
                        ManagerOfServerThread.file(message);
                        break;
                    default:
                        System.out.println("to be continued...");
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
