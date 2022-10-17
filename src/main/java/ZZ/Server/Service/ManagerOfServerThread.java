package ZZ.Server.Service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ManagerOfServerThread {
    private static HashMap<String,ServerConnectClientThread> map
            = new HashMap<>();

    public static void addServerThread(String userName,ServerConnectClientThread scct){
        map.put(userName,scct);
    }

    public static ServerConnectClientThread getServerThread(String userName){
        return map.get(userName);
    }

    public static void getOnlineUsers(String userName,String sender) throws IOException {//查询在线用户
        System.out.println("在线用户列表:");
        String users = "";
        for (Object o: map.keySet()) {
            String id = (String) o;
            users += id+" ";
        }
        ObjectOutputStream oos =
                new ObjectOutputStream(ManagerOfServerThread.
                        getServerThread(userName).getSocket().getOutputStream());
        Message message1 = new Message();
        message1.setMessageType(MessageType.MESSAGE_RETURN_ONLINE_USER);
        message1.setContent(users);
        message1.setReceiver(sender);
        System.out.println(users);
        oos.writeObject(message1);
    }
    public static void msg(Message message) throws IOException {//私聊
        ServerConnectClientThread scct =
                ManagerOfServerThread.
                        getServerThread(message.getReceiver());
        ObjectOutputStream oos =
                new ObjectOutputStream(scct.getSocket().getOutputStream());
        oos.writeObject(message);
    }
    public static void removeServerThread(Message message, String userName, Socket socket) throws IOException {//退出
        System.out.println(message.getSender()+"退出了");
        map.remove(userName);
        socket.close();
    }
    public static void groupMsg(Message message) throws IOException {//群发
        System.out.println(message.getSender()+"群发:"+message.getContent());
        HashMap<String,ServerConnectClientThread> hashMap =
                ManagerOfServerThread.getMap();
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()){
            String id = iterator.next();
            if(!(id.equals(message.getSender()))) {
                ObjectOutputStream oos =
                        new ObjectOutputStream(hashMap.get(id).
                                getSocket().getOutputStream());
                oos.writeObject(message);
            }
        }
    }

    public static void file(Message message) throws IOException {//传输文件
        ObjectOutputStream oos =
                new ObjectOutputStream(ManagerOfServerThread.
                        getServerThread(message.getReceiver()).
                        getSocket().getOutputStream());
        System.out.println(message.getSender()+"发文件给"+message.getReceiver());
        oos.writeObject(message);
    }
    public static HashMap<String,ServerConnectClientThread> getMap(){
        return  map;
    }
}
