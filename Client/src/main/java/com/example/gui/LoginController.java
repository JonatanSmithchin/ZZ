package com.example.gui;

import ZZ.Client.service.UserClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


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

        if(userClientService.checkUser(name,psw)){
            Stage stage = (Stage) userName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
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
