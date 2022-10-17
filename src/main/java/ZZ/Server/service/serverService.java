package ZZ.Server.service;

import java.net.Socket;

public class serverService extends Thread{

    private Socket socket = null;
    private String name = null;

    public serverService(Socket socket,String name){
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("启动线程"+name);
        while (true){

        }
    }
}
