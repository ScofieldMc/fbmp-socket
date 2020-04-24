package com.lj.socket.Service;

import com.alibaba.fastjson.JSON;
import com.lj.socket.entity.User;

import java.io.*;
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
        User user = new User();
        user.setSysId(1);
        user.setAreaId(12);
        user.setUserId("400200001");
        user.setUserName("07000");
        user.setFullName("李歆");
        user.setSocket(null);
        String userStr = JSON.toJSONString(user);
        System.out.println(userStr);
        socket = new Socket("127.0.0.1", port);
        System.out.println("connectied to server...");
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        write.write(userStr);
        write.newLine();
        write.flush();
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    class ClientThread extends Thread {
        private BufferedReader reader;
        private BufferedWriter writer;

        public ClientThread() {
            //子线程初始化构造方法 获取socket监听的端口里传入的数据
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String content = null;
            //死循环 一直读取socket接受到的数据
            while (true) {
                try {
                    if ((content = reader.readLine()) != null) {
                        System.out.println(content);
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
