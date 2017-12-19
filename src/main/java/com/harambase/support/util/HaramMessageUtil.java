package com.harambase.support.util;


import com.harambase.common.HaramMessage;
import org.springframework.http.ResponseEntity;

/**
 * Created by Administrator on 2017/7/6.
 */
public class HaramMessageUtil {

    public static HaramMessage from(ResponseEntity<HaramMessage> responseMessage) {
        HaramMessage msg = new HaramMessage();
        HaramMessage restMessage = responseMessage.getBody();
        if (restMessage == null) {
            return msg;
        }
        msg.setCode(responseMessage.getBody().getCode());
        if (responseMessage.getBody().getMsg() != null)
            msg.setMsg(responseMessage.getBody().getMsg());
        if (responseMessage.getBody().getData() != null)
            msg.setData(responseMessage.getBody().getData());
        return msg;
    }


}
