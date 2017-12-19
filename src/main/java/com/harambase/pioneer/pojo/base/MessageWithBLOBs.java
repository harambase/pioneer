package com.harambase.pioneer.pojo.base;

public class MessageWithBLOBs extends MessageBase {

    private String body;
    
    private String receiverid;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }
    
    public String getReceiverid() {
        return receiverid;
    }
    
    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid == null ? null : receiverid.trim();
    }

    @Override
    public String toString() {
        return "MessageWithBLOBs{" +
                "body='" + body + '\'' +
                ", receiverid='" + receiverid + '\'' +
                "} " + super.toString();
    }
}