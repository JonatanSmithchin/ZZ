package ZZ.Client.service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //每个线程都需要持有一个Socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        //线程在后台保持和服务器通讯
        while(true){
            //等待从服务器中传来的消息
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果Socket中没有Message对象，则会阻塞在readObject
                Message message = (Message) ois.readObject();
                //System.out.println("收到server端socket");
                String messageType = message.getMessageType();
                if (messageType.equals(MessageType.MESSAGE_RETURN_ONLINE_USER)){
                    System.out.println("===Users Online===");
                    String[] onlineUsers = message.getContent().split(" ");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("User:"+onlineUsers[i]);
                    }
                } else if(messageType.equals(MessageType.MESSAGE_COMN_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"对你说:"+message.getContent());
                }else if(messageType.equals(MessageType.MESSAGE_GROUP_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"对所有人说:"+message.getContent());
                }else if(messageType.equals(MessageType.MESSAGE_FILE_MSE)){
                    System.out.println(message.getSender()+"发给你文件到"+message.getMyFile().getDest());
                    FileOutputStream fos = new FileOutputStream(message.getMyFile().getDest());
                    fos.write(message.getMyFile().getFileBytes());
                    fos.close();
                }else{

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

}
