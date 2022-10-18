package ZZ.Client.service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //ÿ���̶߳���Ҫ����һ��Socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        //�߳��ں�̨���ֺͷ�����ͨѶ
        while(true){
            //�ȴ��ӷ������д�������Ϣ
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //���Socket��û��Message�������������readObject
                Message message = (Message) ois.readObject();
                //System.out.println("�յ�server��socket");
                String messageType = message.getMessageType();
                if (messageType.equals(MessageType.MESSAGE_RETURN_ONLINE_USER)){
                    System.out.println("===Users Online===");
                    String[] onlineUsers = message.getContent().split(" ");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("User:"+onlineUsers[i]);
                    }
                } else if(messageType.equals(MessageType.MESSAGE_COMN_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"����˵:"+message.getContent());
                }else if(messageType.equals(MessageType.MESSAGE_GROUP_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"��������˵:"+message.getContent());
                }else if(messageType.equals(MessageType.MESSAGE_FILE_MSE)){
                    System.out.println(message.getSender()+"�������ļ���"+message.getMyFile().getDest());
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
