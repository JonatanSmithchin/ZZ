package ZZ.Client;

import ZZ.Client.service.UserClientService;

import java.util.Scanner;

public class View {

    public static void main(String[] args) {
        View view = new View();
        view.menu();
    }

    private Scanner scanner = new Scanner(System.in);

    private UserClientService userClientService = new UserClientService();
    public View(){

    }

    private void menu(){
        boolean loop = true;
        int key;

        while(loop){
            System.out.println("登录...");
            System.out.println("1.登录");
            System.out.println("2.退出\n");
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
                    }else{
                        System.out.println("登陆失败。。。");
                    }

                    break;
                case 2:
                    System.out.println("退出成功。。。");
                    loop = false;
                    break;

            }
        }
    }
}
