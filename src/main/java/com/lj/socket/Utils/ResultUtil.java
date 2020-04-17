package com.lj.socket.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtil {
    private boolean success;
    private String message;
    private Object data;

    public static ResultUtil result(boolean success , String message , Object data){
        return new ResultUtil(success , message , data);
    }
    public static ResultUtil success(Object data){
        return new ResultUtil(true,"", data);
    }
    public static ResultUtil error(String message){
        return new ResultUtil(false, message,null);
    }
    public static ResultUtil success(String message){
        return new ResultUtil(true , message ,null);
    }

}