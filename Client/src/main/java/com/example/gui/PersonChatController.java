package com.example.gui;

import ZZ.domain.Message;
import com.example.gui.Client.service.ClientFileService;
import com.example.gui.Client.service.ManagerOfClientThread;
import com.example.gui.Client.service.MessageClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PersonChatController {

    @FXML
    private TextArea textArea;

    @FXML
    private Button backButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button sendButton;

    @FXML
    private Button sendfileButton;

    @FXML
    private VBox dialogBox;

    private String receiver;

    private String sender;

    public void init(){

        HashMap map = (HashMap) textArea.getScene().getUserData();
        sender = (String) map.get("sender");
        receiver = (String) map.get("receiver");
    }

    @FXML
    void backToMain(MouseEvent event) {
        Stage stage = (Stage)backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clearTextArea(MouseEvent event) {
        textArea.setText("");
    }

    @FXML
    void sendMessage(MouseEvent event) {
        String context = textArea.getText();
        System.out.println(ManagerOfClientThread.getClientThread(sender));
        MessageClientService.personalChat(sender,receiver,context);
        textArea.setText("");
        Label labelContext = new Label();
        Label labelTime = new Label();
        labelContext.setText(receiver+": "+context);
        labelTime.setStyle("-fx-text-fill: blue");
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        labelTime.setText(dataFormat.format(new Date()));
        System.out.println(new Date());

        dialogBox.getChildren().addAll(labelTime,labelContext);
    }

    public void updateDialog(Message message)  {
        Label labelContext = new Label();
        Label labelTime = new Label();
        labelContext.setText(message.getSender()+": "+message.getContent());
        labelTime.setStyle("-fx-text-fill: blue");
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        labelTime.setText(dataFormat.format(message.getSendTime()));

        dialogBox.getChildren().addAll(labelTime,labelContext);
    }

    @FXML
    private void sendFile(MouseEvent event){
        System.out.println("send:");
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(sendfileButton.getScene().getWindow());
        System.out.println(file);
        ClientFileService.sendFile(sender,receiver,"D:/receive/"+file.getName(),file.getAbsolutePath());
    }

}