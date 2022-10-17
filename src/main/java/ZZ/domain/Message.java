package ZZ.domain;

import java.io.Serializable;

public class Message implements Serializable {

    private String messageType;//根据接口常量定义消息类型

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
