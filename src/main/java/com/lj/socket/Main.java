package com.lj.socket;

import com.lj.socket.Service.Server;
import com.lj.socket.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;


@SpringBootApplication
public class Main {
    public static ArrayList<User> users = new ArrayList<User>();
    public static void main(String[] args){
        SpringApplication.run(Main.class,args );
        Server server = new Server();
        try {
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
