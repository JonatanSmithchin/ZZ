package com.example.gui;

import com.example.gui.Client.service.ClientConnectServerThread;
import com.example.gui.Client.service.UserClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class SignInController {

    @FXML
    private TextField RePasswordText;

    @FXML
    private Button confirm;

    @FXML
    private TextField passwordText;

    @FXML
    private Button reset;

    @FXML
    private TextField userNameText;

    @FXML
    protected void onConfirmButtonClick(MouseEvent event) throws IOException {
        String userName = userNameText.getText();
        String password = passwordText.getText();
        String rePassword = RePasswordText.getText();

        if (!password.equals(rePassword)){

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("disMatch-pop.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            passwordText.setText("");
            RePasswordText.setText("");
            return;
        }
/// TODO: 2022/10/18 增加判断注册成功逻辑
// TODO: 2022/10/21
        UserClientService userClientService = new UserClientService();

        Socket socket = userClientService.signIn(userName,password);
        if(socket!=null){
            Stage stage = (Stage) userNameText.getScene().getWindow();
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
            map.put("userName",userName);
            map.put("password",password);
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
        System.out.println(userName+password+rePassword);
    }

    @FXML
    protected void onResetButtonClick(MouseEvent event) {
        userNameText.setText("");
        passwordText.setText("");
        RePasswordText.setText("");
    }
}
