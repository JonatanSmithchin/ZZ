package com.example.gui;

import ZZ.domain.Message;
import com.example.gui.Client.service.ManagerOfClientThread;
import com.example.gui.Client.service.MessageClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    private MessageClientService messageClientService;

    private String receiver;

    private String sender;

    public void init(){
        messageClientService = new MessageClientService();
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
    void sendFile(MouseEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) {
        String context = textArea.getText();
        System.out.println(ManagerOfClientThread.getClientThread(sender));
        messageClientService.personalChat(sender,receiver,context);
    }

    public void updateDialog(Message message){
        Label label = new Label();
        label.setText(message.getContent());
        dialogBox.getChildren().add(label);
    }

}