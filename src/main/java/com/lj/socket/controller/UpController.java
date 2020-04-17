package com.lj.socket.controller;

import com.lj.socket.Main;
import com.lj.socket.Service.Server;
import com.lj.socket.Utils.DataCheckUtil;
import com.lj.socket.Utils.ResultUtil;
import com.lj.socket.entity.SendData;
import com.lj.socket.entity.SysUpgrade;
import com.lj.socket.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Controller
public class UpController {
    @RequestMapping("/hello")
    public String fun(){
        System.out.println("Im index");
        return "hello";
    }

    @PostMapping("/pub-upgrade")
    @ResponseBody
    public ResultUtil pubUpgrade(SysUpgrade sysUpgrade){
        if(DataCheckUtil.SysUpgradeCheck(sysUpgrade)){
            //构建数据
            SendData sendData = new SendData("fbmp_sys_upgrade",sysUpgrade);


            //异步调用推送方法
            ExecutorService executor= Executors.newFixedThreadPool(1);
            Future<Boolean> future = executor.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    List<User> itemUsers = new ArrayList<User>();
                    List<Integer> areaId = Arrays.asList(sysUpgrade.getAreaId());


                    //筛选符合推送条件的User存入itemUsers列表中
                    Server.removeCloseClient();
                    for (User user: Main.users) {
                        if(user.getSysId().equals(sysUpgrade.getSysId()) && areaId.contains(user.getAreaId())){
                            itemUsers.add(user);
                        }
                    }
                    //调用Send方法推送数据
                    return Server.send(itemUsers,sendData);
                }
            });

            return ResultUtil.success("Succeed");
        }else {
            return ResultUtil.error("Data Error!");
        }

    }


}
