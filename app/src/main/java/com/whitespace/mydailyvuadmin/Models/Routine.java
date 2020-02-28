package com.whitespace.mydailyvuadmin.Models;

import java.util.List;

public class Routine {

    private String startTime,endTime,subject,department,
            teacher,routine,room,
            am_pm,semester,section,day;
    private int orderHour,orderMinute;
     private List<String> teachers;

    public Routine() {
    }

    public Routine(String startTime, String endTime, String subject, String department, String teacher, String routine, String room, String am_pm, String semester, String section, String day, int orderHour, int orderMinute, List<String> teachers) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.department = department;
        this.teacher = teacher;
        this.routine = routine;
        this.room = room;
        this.am_pm = am_pm;
        this.semester = semester;
        this.section = section;
        this.day = day;
        this.orderHour = orderHour;
        this.orderMinute = orderMinute;
        this.teachers = teachers;
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

    public int getOrderHour() {
        return orderHour;
    }

    public void setOrderHour(int orderHour) {
        this.orderHour = orderHour;
    }

    public int getOrderMinute() {
        return orderMinute;
    }

    public void setOrderMinute(int orderMinute) {
        this.orderMinute = orderMinute;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
}
