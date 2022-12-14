package com.example.gui;

import com.example.gui.Client.service.ClientConnectServerThread;
import com.example.gui.Client.service.ManagerOfClientThread;
import com.example.gui.Client.service.UserClientService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;


public class MainPageController {

    @FXML
    private Label user;

    @FXML
    private TitledPane userOnline;

    @FXML
    private VBox vBox ;

    @FXML
    private Button logout;

    private double layoutY = 2.0;

    private static final double width = 24.0;

    private HashMap<String,Object> map;

    private UserClientService userClientService;

    //private Scene scene;
    //private PersonChatController personChatController;

    private ClientConnectServerThread clientConnectServerThread;

    private String[] usersBeforeUpdate ;

    private HashMap<String,Stage> stageHashMap;

    private Socket socket;

    public MainPageController() throws IOException {
        super();
        System.out.println("hello");
    }

    void init() throws IOException {


        HashMap<String,Object> map = (HashMap)user.getScene().getUserData();
        String name = (String) map.get("userName");
        socket = (Socket) map.get("socket");
        user.setText(name);
        userClientService = (UserClientService) map.get("service");
        //scene = (Scene) map.get("scene");

        //personChatController = (PersonChatController) map.get("personChatController");
        //System.out.println(personChatController);
        userOnline.setExpanded(true);
        usersBeforeUpdate = null;
        stageHashMap = new HashMap<>();


        clientConnectServerThread = (ClientConnectServerThread) map.get("thread");
        if(!clientConnectServerThread.isAlive()){
            clientConnectServerThread.start();
        }
        ManagerOfClientThread.addClientThread(name,clientConnectServerThread);
        getUsers();
        System.out.println(socket.getPort());
        System.out.println();

    }

    @FXML
    void getUsers(MouseEvent event) {

       userClientService.onlineUserList();
    }

    void getUsers(){
        System.out.println(ManagerOfClientThread.getClientThread(user.getText()));
        userClientService.onlineUserList();
    }

    public void updateUsers(String[] userNames){
        System.out.println(userNames.length);

        if (userOnline.isExpanded()){
            if(!Arrays.equals(usersBeforeUpdate, userNames)){
                vBox.getChildren().remove(1,usersBeforeUpdate==null?1:usersBeforeUpdate.length);
                usersBeforeUpdate = userNames;
                for (int i = 0;i<userNames.length;i++){
                    Label label = new Label();

                    label.setLayoutX(10.0);
                    label.setLayoutY(layoutY);
                    label.setPrefWidth(335.0);
                    label.setPrefHeight(15.0);
                    layoutY+=width;
                    label.setText(userNames[i]);
                    label.setFont(new Font("Microsoft YaHei",18));

                    HashMap<String,Object> nextMap = new HashMap<>();
                    nextMap.put("receiver",label.getText());
                    nextMap.put("sender",user.getText());
                    //?????????????????????????????????????????????HashMap??????
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personChat-view.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    scene.setUserData(nextMap);
                    PersonChatController personChatController = fxmlLoader.getController();
                    clientConnectServerThread.addPersonChatController(label.getText(),personChatController);
                    personChatController.init();
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(label.getText());
                    stageHashMap.put(label.getText(),stage);
                    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            stageHashMap.get(label.getText()).show();
                        }
                    });
                    vBox.getChildren().add(label);
                }
            }
        }
    }

    public boolean popReceiveFile(String sender,String fileName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("??????????????????"+sender+"?????????"+fileName+"???");
        alert.showAndWait();
        ButtonType buttonType = alert.getResult();
        if (buttonType==ButtonType.OK) return true;
        else return false;
    }

    @FXML
    void onLogoutButtonClick(MouseEvent event) {
        Stage stage = (Stage)logout.getScene().getWindow();
        userClientService.logout();
        stage.close();
    }

}
