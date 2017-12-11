package com.harambase.common.util;

import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Pin;
import org.apache.shiro.SecurityUtils;

public class SessionUtil {

    public static Person getUser(){
        return (Person)SecurityUtils.getSubject().getSession().getAttribute("user");
    }

    public static String getUserId(){
        return ((Person)SecurityUtils.getSubject().getSession().getAttribute("user")).getUserid();
    }

    public static Pin getPin(){
        return ((Pin) SecurityUtils.getSubject().getSession().getAttribute("pin"));
    }

    public static void setPin(Object pin) {
        SecurityUtils.getSubject().getSession().setAttribute("pin", pin);
    }
}
