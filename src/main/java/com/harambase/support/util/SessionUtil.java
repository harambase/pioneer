package com.harambase.support.util;

import com.harambase.pioneer.pojo.Person;

import java.util.LinkedHashMap;

public class SessionUtil {

    public static Person getUser() {
//        return (Person) SecurityUtils.getSubject().getSession().getAttribute("user");
        return new Person();
    }

    public static String getUserId() {
//        return ((Person) SecurityUtils.getSubject().getSession().getAttribute("user")).getUserId();
        return "9201701000";
    }

    public static LinkedHashMap getPin() {
//        return ((LinkedHashMap) SecurityUtils.getSubject().getSession().getAttribute("pin"));
        return new LinkedHashMap();
    }

    public static void setPin(Object pin) {
//        SecurityUtils.getSubject().getSession().setAttribute("pin", pin);
    }
}
