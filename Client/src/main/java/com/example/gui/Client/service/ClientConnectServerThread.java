package com.example.gui.Client.service;

import ZZ.domain.Message;
import ZZ.domain.MessageType;
import com.example.gui.HelloApplication;
import com.example.gui.MainPageController;
import com.example.gui.PersonChatController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientConnectServerThread extends Thread{
    //ÿ���̶߳���Ҫ����һ��Socket
    private Socket socket;

    MainPageController mainPageController;

    PersonChatController personChatController;

    public ClientConnectServerThread(Socket socket,MainPageController mainPageController,PersonChatController personChatController){
        this.socket=socket;
        this.mainPageController = mainPageController;
        this.personChatController = personChatController;
    }

    public void setPersonChatController(PersonChatController personChatController) {
        this.personChatController = personChatController;
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
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainPageController.updateUsers(onlineUsers);
                            }
                        });

                        System.out.println("User:"+onlineUsers[i]);
                    }
                } else if(messageType.equals(MessageType.MESSAGE_COMN_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"����˵:"+message.getContent());
                    Platform.runLater((new Runnable() {
                        @Override
                        public void run() {
                            if(personChatController == null){
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personChat-view.fxml"));
                                Scene scene = null;
                                try {
                                    scene = new Scene(fxmlLoader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                HashMap<String,Object> map = new HashMap<>();
                                map.put("sender",message.getSender());
                                map.put("receiver",message.getReceiver());
                                scene.setUserData(map);
                                PersonChatController personChatController = fxmlLoader.getController();
                                setPersonChatController(personChatController);
                                personChatController.init();
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle(message.getSender());
                                stage.show();
                            }


                            personChatController.updateDialog(message);
                        }
                    }));
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