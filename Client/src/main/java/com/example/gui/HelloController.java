package com.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button login;

    @FXML
    private Button signIn;

    @FXML
    protected void onLoginButtonClick(MouseEvent event) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene loginScene = new Scene(loginLoader.load());

        Stage stage = (Stage) login.getScene().getWindow();

        stage.setScene(loginScene);
        stage.show();
        System.out.println("hello");
    }

    @FXML
    protected void onSignInButtonClick() throws IOException {
        FXMLLoader signInLoader = new FXMLLoader(HelloApplication.class.getResource("signIn-view.fxml"));
        Scene loginScene = new Scene(signInLoader.load());

        Stage stage = (Stage) login.getScene().getWindow();

        stage.setScene(loginScene);
        stage.show();
        System.out.println("hello");
    }


}
