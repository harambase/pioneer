package com.harambase.support.util;

import com.harambase.pioneer.pojo.Person;
import org.apache.shiro.SecurityUtils;

import java.util.LinkedHashMap;

public class SessionUtil {

    public static Person getUser() {
        return (Person) SecurityUtils.getSubject().getSession().getAttribute("user");
    }

    public static String getUserId() {
        return ((Person) SecurityUtils.getSubject().getSession().getAttribute("user")).getUserId();
    }

    public static LinkedHashMap getPin() {
        return ((LinkedHashMap) SecurityUtils.getSubject().getSession().getAttribute("pin"));
    }

    public static void setPin(Object pin) {
        SecurityUtils.getSubject().getSession().setAttribute("pin", pin);
    }
}
