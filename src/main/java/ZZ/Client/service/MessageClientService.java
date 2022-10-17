package ZZ.Client.service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {

    public void personalChat(String sender, String receiver, String content){
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageType(MessageType.MESSAGE_COMN_MSG);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(sender+"对"+receiver+"说:"+content);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManagerOfClientThread.
                                    getClientThread(sender).
                                    getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void groupChat(String sender, String content){
        Message message = new Message();
        message.setSender(sender);
        message.setContent(content);
        message.setMessageType(MessageType.MESSAGE_GROUP_MSG);
        message.setSendTime(new Date().toString());
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManagerOfClientThread.
                                    getClientThread(sender).
                                    getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
