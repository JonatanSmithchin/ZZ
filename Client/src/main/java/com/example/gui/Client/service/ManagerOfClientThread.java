package com.example.gui.Client.service;

import java.util.HashMap;

public class ManagerOfClientThread {
    private static HashMap<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addClientThread(String userName, ClientConnectServerThread ccst){
        map.put(userName,ccst);
    }

    public static ClientConnectServerThread getClientThread(String userName){
        return map.get(userName);
    }
}
