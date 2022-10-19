package com.example.gui.Client.service;


import ZZ.domain.Message;
import ZZ.domain.MessageType;
import ZZ.domain.MyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ClientFileService {
    /***
     *
     * @param sender 发送方
     * @param receiver 接收方
     * @param dest 目标路径
     * @param src 源路径
     */
    public void sendFile(String sender, String receiver, String dest, String src){

        File file =new File(src);
        byte[] fileBytes = new byte[(int)file.length()];
        MyFile myFile = new MyFile(fileBytes, fileBytes.length, dest, src);
        Message message = new Message();


        FileInputStream fis = null;

        try {
            fis = new FileInputStream(src);
            fis.read(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        message.setMyFile(myFile);
        message.setMessageType(MessageType.MESSAGE_FILE_MSE);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSendTime(new Date().toString());
        System.out.println(sender+"发给"+receiver);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManagerOfClientThread.
                            getClientThread(sender).
                            getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
