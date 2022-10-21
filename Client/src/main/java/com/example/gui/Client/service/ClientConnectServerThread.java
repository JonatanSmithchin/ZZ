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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ClientConnectServerThread extends Thread{
    //ÿ���̶߳���Ҫ����һ��Socket
    private Socket socket;

    MainPageController mainPageController;

    HashMap<String,PersonChatController>  onlinePersonChatController;

    public ClientConnectServerThread(Socket socket,MainPageController mainPageController){
        this.socket=socket;
        this.mainPageController = mainPageController;
        this.onlinePersonChatController = new HashMap<>();
    }

    public void addPersonChatController(String onlineUser,PersonChatController personChatController) {
        onlinePersonChatController.put(onlineUser,personChatController);
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
                    String[] onlineUsers = ((String) message.getContent()).split(" ");
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
//                            if(personChatController == null){
//                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personChat-view.fxml"));
//                                Scene scene = null;
//                                try {
//                                    scene = new Scene(fxmlLoader.load());
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                HashMap<String,Object> map = new HashMap<>();
//                                map.put("sender",message.getReceiver());
//                                map.put("receiver",message.getSender());//反转receiver和sender才能建立正确的客户端聊天界面，得到正确的线程
//                                map.put("clientThread",ManagerOfClientThread.getClientThread(message.getReceiver()));
//                                scene.setUserData(map);
//                                PersonChatController personChatController = fxmlLoader.getController();
//                                setPersonChatController(personChatController);
//                                System.out.println(ManagerOfClientThread.getClientThread(message.getReceiver()));
//                                personChatController.init();
//                                Stage stage = new Stage();
//                                stage.setScene(scene);
//                                stage.setTitle(message.getSender());
//                                stage.show();
//                            }


                            onlinePersonChatController.get(message.getSender()).updateDialog(message);
                        }
                    }));
                }else if(messageType.equals(MessageType.MESSAGE_GROUP_MSG)){
                    System.out.println(message.getSendTime()+" "
                            +message.getSender()+"��������˵:"+message.getContent());
                }else if(messageType.equals(MessageType.MESSAGE_FILE_MSE)){
                    System.out.println(message.getSender()+"�������ļ���"+message.getMyFile().getDest());

                    final FutureTask<Boolean> confirm = new FutureTask<>(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return mainPageController.popReceiveFile(message.getSender(),message.getMyFile().getSrc());
                        }
                    });

                    Platform.runLater(confirm);

                    if (confirm.get()){
                        FileOutputStream fos = new FileOutputStream(message.getMyFile().getDest());
                        fos.write(message.getMyFile().getFileBytes());
                        fos.close();
                    }

                }else{

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
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
