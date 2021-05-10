package com.netcracker.lab3webApp.models;

public class Group {


    private Long id;
    private int groupNumber;

    private String educationForm;

    public Group() {
    }

    public Group(int groupNumber, String educationForm) {
        this.groupNumber = groupNumber;
        this.educationForm = educationForm;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
