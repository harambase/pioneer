package com.harambase.common.constant;

/**
 * Created by Administrator on 2017/5/8.
 */
public enum FlagDict {
    /**
     * 公共flag
     */
    SUCCESS("操作成功",2001),
    FAIL("操作失败",2002),
    ILLEGAL_PARAM("参数有误",2003),
    SENSITIVE_WORD("含有非法字符",2004),
    SYSTEM_ERROR("系统异常",2005),
    TOKEN_SUCCESS("获取Token成功", 2006),
    USER_NAME_EXISTS("用户已存在", 2007),
    USER_DISABLED("用户已禁用", 2008),
    USER_BLOCKED("用户已截断", 2009),
    GROUP_NAME_EXISTS("组名已存在", 2010),
    GROUP_DISABLED("组已禁用", 2011),
    GROUP_NOT_EXISTS("组不存在", 2012),
    UNMET_PREREQ("预选课程未完成", 2013),
    COURSE_DISABLED("课程禁止选课", 2014),
    MAX_CAPACITY("课程容量上限", 2015),
    TIMECONFLICT("时间冲突", 2016);

    // 成员变量
    private String m;
    private int v;

    FlagDict(String m , int v){
        this.m = m;
        this.v = v;
    }

    public String getM() {
        return m;
    }

    public int getV() {
        return v;
    }

}
