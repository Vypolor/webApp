package com.netcracker.lab3webApp.models;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

public class Student {

    @Min(value = 1000, message = "Student ID should be greater than 1000")
    @Max(value = 9999, message = "Student ID should be less than 9999")
    private int studentID;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 40, message = "Name should be less than 40 characters")
    private String name;

    @NotEmpty(message = "Second Name should not be empty")
    @Size(max = 40, message = "Second Name should be less than 40 characters")
    private String secondName;

    private String patronymic;
    private String educationForm;
    private Date birthday;
    private int groupNumber;

    public Student() {
    }

    public Student(int studentID, String name, String secondName, String patronymic,
                   String educationForm, Date birthday, int groupNumber) {
        this.studentID = studentID;
        this.name = name;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.educationForm = educationForm;
        this.birthday = birthday;
        this.groupNumber = groupNumber;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
