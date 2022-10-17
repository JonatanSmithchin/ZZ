package ZZ.Client;

import ZZ.Client.service.ClientFileService;
import ZZ.Client.service.MessageClientService;
import ZZ.Client.service.UserClientService;

import java.util.Scanner;

public class View {

    public static void main(String[] args) throws InterruptedException {
        View view = new View();
        view.menu();
    }

    private Scanner scanner = new Scanner(System.in);
    MessageClientService messageClientService = new MessageClientService();
    ClientFileService clientFileService = new ClientFileService();
    UserClientService userClientService = new UserClientService();
    public View(){

    }

    private void menu() throws InterruptedException {
        boolean loop = true;
        int key;

        while(loop){
            System.out.println("登录...");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.退出\n");
            System.out.println("输入选择：");
            key = scanner.nextInt();
            switch (key){
                case 1://实现登录操作
                    System.out.print("userName:");
                    String userName = scanner.next();
                    System.out.print("password:");
                    String password = scanner.next();
                    boolean isSuccess =
                            userClientService.checkUser(userName,password);

                    if(isSuccess){
                        System.out.println("登陆成功。。。");
                        SecondMenu(userName);
                    }else{
                        System.out.println("登陆失败。。。");
                    }

                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("退出成功。。。");
                    userClientService.exit();
                    loop = false;
                    break;

            }
        }
    }

    private void SecondMenu(String userId) throws InterruptedException {
        boolean loop = true;
        int key;
        String content = "";
        String receiver = "";

        while (loop){
            System.out.println("用户-"+userId+":");
            System.out.println("1 在线用户");
            System.out.println("2 群发消息");
            System.out.println("3 私聊");
            System.out.println("4 发送文件");
            System.out.println("5 退出");

            key=scanner.nextInt();

            switch (key){
                case 1:
                    userClientService.onlineUserList();
                    Thread.sleep(1000);
                    //显示在线人数
                    break;
                case 2:
                    System.out.println("内容:");
                    content = scanner.next();
                    messageClientService.groupChat(userId,content);
                    //群发
                    break;
                case 3:
                    System.out.print("输入在线UserId:");
                    receiver = scanner.next();
                    System.out.print("内容:");
                    content = scanner.next();
                    messageClientService.personalChat(userId,receiver,content);
                    break;
                case 4:
                    System.out.print("输入在线UserId:");
                    receiver = scanner.next();
                    System.out.println("文件路径:");
                    String filePath = scanner.next();
                    System.out.println("发送到:");
                    String fileDest = scanner.next();
                    clientFileService.sendFile(userId,receiver,fileDest,filePath);
                    //发文件
                    break;
                case 5:
                    //退出
                    loop = false;
                    break;
            }
        }


    }
}
