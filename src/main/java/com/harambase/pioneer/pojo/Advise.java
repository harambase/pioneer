package com.harambase.pioneer.pojo;

import com.harambase.pioneer.pojo.base.AdviseBase;

public class Advise extends AdviseBase {

    private String sname;
    private String fname;
    private String oname;

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
