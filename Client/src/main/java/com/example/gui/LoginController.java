package com.example.gui;

import com.example.gui.Client.service.ClientConnectServerThread;
import com.example.gui.Client.service.ManagerOfClientThread;
import com.example.gui.Client.service.UserClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class LoginController {

    @FXML
    public TextField userName;
    @FXML
    public TextField password;

    @FXML
    public Button submit;

    @FXML
    public Button back;

    public Stage stage;
    
    @FXML
    protected void onSubmitButtonClick() throws IOException {

        String name = userName.getText();
        String psw =password.getText();
        System.out.println(name+psw);
        UserClientService userClientService = new UserClientService();

        Socket socket = userClientService.checkUser(name,psw);
        if(socket!=null){
            Stage stage = (Stage) userName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainPageController mainPageController = fxmlLoader.getController();
//            FXMLLoader chatFxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personChat-view.fxml"));
//            System.out.println(chatFxmlLoader);
//            PersonChatController personChatController = chatFxmlLoader.getController();
//            System.out.println(personChatController);
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket,mainPageController);
            //ManagerOfClientThread.addClientThread(name,clientConnectServerThread);
            //System.out.println(ManagerOfClientThread.getClientThread(name));
            Map<String,Object> map = new HashMap<>();
            map.put("userName",name);
            map.put("password",psw);
            map.put("socket",socket);
            map.put("service",userClientService);
            map.put("thread",clientConnectServerThread);
//            map.put("personChatController",personChatController);
//            map.put("scene",new Scene(chatFxmlLoader.load()));
            scene.setUserData(map);
            mainPageController.init();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    protected void onReturnButtonClick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
