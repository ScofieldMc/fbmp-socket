package com.lj.socket.Service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lj.socket.Main;
import com.lj.socket.entity.SendData;
import com.lj.socket.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {

    private int port = 9000;


    public void start() throws IOException {

        System.out.println(port);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务启动成功");
        while (true) {
            Socket socket = serverSocket.accept();
            byte[] b = new byte[1024];
            InputStream in = socket.getInputStream();
            in.read(b,0,1024);
            String userStr = new String(b,"utf-8");


            try {
                JSONObject userJson = JSON.parseObject(userStr);
                //接收客户端的信息
                System.out.println("======================="+userJson);
                User user = JSON.toJavaObject(userJson, User.class);

                //封装socket
                user.setSocket(socket);
                Main.users.add(user);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
    //判断客户端是否断开连接（发送一个test判断）
    public static boolean isCloseed(Socket socket){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("");
            writer.newLine();
            writer.flush();
            SendData sendData = new SendData("test", null);
            String senddata = JSON.toJSONString(sendData);
            writer.write(senddata);
            writer.newLine();
            writer.flush();
            return false;
        }catch (Exception e){
           // e.printStackTrace();
            return true;

        }

    }

    public static  boolean send(List<User> itemUsers, SendData sendData){
        try{
            for (User user : itemUsers) {
                transput(user.getSocket(),sendData);
            }
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

    }
    //发送方法
    public static void transput(Socket socket,SendData sendData) throws IOException {
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        write.write(JSON.toJSONString(sendData));
        write.newLine();
        write.flush();
    }

    //删除断开连接的
    public static void removeCloseClient(){
        //System.out.println("befor:"+Main.users);
        List<User> tmpUsers = new ArrayList<User>();

        for (User user : Main.users) {

            if(isCloseed(user.getSocket())){
                tmpUsers.add(user);
            }
        }
        for (User user : tmpUsers) {
            Main.users.remove(user);
        }

        //System.out.println("after:"+Main.users);
        System.out.println("在线人数为："+Main.users.size());
    }

}
