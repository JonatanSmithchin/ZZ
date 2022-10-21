package ZZ.domain;

public interface MessageType {
    final String MESSAGE_LOGIN_SUCCEED = "1";//登录成功
    final String MESSAGE_LOGIN_FAILED = "2";//登录失败
    String MESSAGE_COMN_MSG = "3";//普通信息包
    String MESSAGE_GROUP_MSG = "4";
    String MESSAGE_GET_ONLINE_USER = "5";//要求返回在线用户列表
    String MESSAGE_RETURN_ONLINE_USER = "6";
    String MESSAGE_CLIENT_EXIT = "7";//请求退出
    String MESSAGE_FILE_MSE = "8";

}
