package com.example.wacko;

public class Tracks {

    private String tname,turl;

    public Tracks(){

    }

    public Tracks(String tname,String turl){

        this.tname = tname;
        this.turl = turl;

    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
