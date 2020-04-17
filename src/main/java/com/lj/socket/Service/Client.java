package com.lj.socket.Service;

import com.lj.socket.entity.SendData;
import com.lj.socket.entity.User;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private int port = 9000;
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        System.out.println(port);
        socket = new Socket("127.0.0.1", port);
        System.out.println("connectied to server...");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        User user = new User();
        user.setSysId(1);
        user.setAreaId(12);
        user.setUserId("400200001");
        user.setUserName("07000");
        user.setFullName("李歆");
        user.setSocket(null);
        out.writeObject(user);
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    class ClientThread extends Thread {
        private ObjectInputStream in;
        private InputStream sin;

        public ClientThread() {
            //子线程初始化构造方法 获取socket监听的端口里传入的数据
            try {
                sin = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            SendData sd = null;
            //死循环 一直读取socket接受到的数据
            while (true) {
                try {
                    in = new ObjectInputStream(sin);
                    if ((sd = (SendData) in.readObject()) != null) {
                        System.out.println(sd);

                    }
                   
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("与服务断开链接");
                    e.printStackTrace();
                    break;

                }
            }
        }
    }
}
