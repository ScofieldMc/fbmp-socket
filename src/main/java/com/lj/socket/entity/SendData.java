package com.lj.socket.entity;

import java.io.Serializable;

public class SendData implements Serializable{
    private String key;
    private Object data;

    public SendData() {
    }

    public SendData(String key, Object data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendData{" +
                "key='" + key + '\'' +
                ", data=" + data +
                '}';
    }
}