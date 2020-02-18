package com.whitespace.mydailyvuadmin.Models;

public class Notification {
    public String order,title,desc,date;

    public Notification() {
    }

    public Notification(String order, String title, String desc, String date) {
        this.order = order;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
