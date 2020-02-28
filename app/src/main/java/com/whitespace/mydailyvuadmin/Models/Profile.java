package com.whitespace.mydailyvuadmin.Models;

import java.util.List;

public class Profile {

    private String imageUrl,name,email,department,semester,section,id,contact,counseling_hour,office,designation,status;

    public Profile() {
    }

    public Profile(String imageUrl, String name, String email, String department, String semester, String section, String id, String contact, String counseling_hour, String office, String designation, String status) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.department = department;
        this.semester = semester;
        this.section = section;
        this.id = id;
        this.contact = contact;
        this.counseling_hour = counseling_hour;
        this.office = office;
        this.designation = designation;
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCounseling_hour() {
        return counseling_hour;
    }

    public void setCounseling_hour(String counseling_hour) {
        this.counseling_hour = counseling_hour;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
