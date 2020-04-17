package com.lj.socket.Service;


import com.lj.socket.Main;
import com.lj.socket.entity.SendData;
import com.lj.socket.entity.SysUpgrade;
import com.lj.socket.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Server {

    private int port = 9000;


    public void start() throws IOException {

        System.out.println(port);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务启动成功");
        while (true) {
            Socket socket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                //接收客户端的信息
                User user = (User)in.readObject();
                System.out.println(user);
                //封装socket
                user.setSocket(socket);
                Main.users.add(user);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }
    //判断客户端是否断开连接（发送一个test判断）
    public static boolean isCloseed(Socket socket){
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new SendData("test",null));
            return false;
        }catch (Exception e){
            return true;

        }

    }
    public static void transput(Socket socket,SendData sendData) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(sendData);
    }
    //****************************************************************************************
    public static  boolean send(List<User> itemUsers,SendData sendData){
        //removeCloseClient();
        SysUpgrade sysUpgrade = (SysUpgrade) sendData.getData();
        List<Integer> areaId = Arrays.asList(sysUpgrade.getAreaId());
       try{
           for (User user : itemUsers) {
               transput(user.getSocket(),sendData);
           }
           return true;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }

    }
    //**************************************************************************************************
    public static void removeCloseClient(){
        List<User> tmpUsers = new ArrayList<User>();

        for (User user : Main.users) {
            if(isCloseed(user.getSocket())){
                tmpUsers.add(user);
            }
        }
        for (User user : tmpUsers) {
            Main.users.remove(user);
        }
        System.out.println("在线人数为："+Main.users.size());
    }

}
