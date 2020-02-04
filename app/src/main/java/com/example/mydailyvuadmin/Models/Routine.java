package com.example.mydailyvuadmin.Models;

public class Routine {

    public String startTime,endTime,subject,department,teacher,routine,room,orderHour,orderMinute,am_pm,semester,section,day;

    public Routine() {
    }

    public Routine(String startTime, String endTime, String subject, String department, String teacher, String routine, String room, String orderHour, String orderMinute, String am_pm, String semester, String section, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.department = department;
        this.teacher = teacher;
        this.routine = routine;
        this.room = room;
        this.orderHour = orderHour;
        this.orderMinute = orderMinute;
        this.am_pm = am_pm;
        this.semester = semester;
        this.section = section;
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOrderHour() {
        return orderHour;
    }

    public void setOrderHour(String orderHour) {
        this.orderHour = orderHour;
    }

    public String getOrderMinute() {
        return orderMinute;
    }

    public void setOrderMinute(String orderMinute) {
        this.orderMinute = orderMinute;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
