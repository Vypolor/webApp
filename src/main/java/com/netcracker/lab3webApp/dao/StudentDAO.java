package com.netcracker.lab3webApp.dao;

import com.netcracker.lab3webApp.models.Student;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDAO {

    private static Connection connection = ConnectionInitializer.getConnection();

    public List<Student> index(int groupNumber) {
        List<Student> students = new ArrayList<>();

        try {
            PreparedStatement getGroupIdStatement =
                    connection.prepareStatement("SELECT id FROM groups WHERE group_num=?");

            getGroupIdStatement.setInt(1, groupNumber);
            ResultSet resultSet = getGroupIdStatement.executeQuery();
            resultSet.next();

            int id = resultSet.getInt("id");
            getGroupIdStatement.close();


            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM students WHERE group_num=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet2 = preparedStatement.executeQuery();
            while (resultSet2.next()){
                Student student = new Student();

                student.setStudentID(resultSet2.getInt("student_id"));
                student.setName(resultSet2.getString("first_name"));
                student.setSecondName(resultSet2.getString("second_name"));
                student.setPatronymic(resultSet2.getString("patronymic"));
                boolean form = resultSet2.getBoolean("education_form");
                if(form){
                    student.setEducationForm("GOVERNMENT");
                }
                else {
                    student.setEducationForm("PAID");
                }
                student.setGroupNumber(resultSet2.getInt("group_num"));
                student.setBirthday(resultSet2.getDate("birthday"));

                students.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return students;
    }

    public Student show(int studentID){

        Student student = null;

        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM students WHERE student_id=?");

            preparedStatement.setInt(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            student = new Student();
            student.setStudentID( resultSet.getInt("student_id"));
            student.setName(      resultSet.getString("first_name"));
            student.setSecondName(resultSet.getString("second_name"));
            student.setPatronymic(resultSet.getString("patronymic"));
            boolean form = resultSet.getBoolean("education_form");
            if(form){
                student.setEducationForm("GOVERNMENT");
            }
            else {
                student.setEducationForm("PAID");
            }
            student.setGroupNumber(resultSet.getInt("group_num"));
            student.setBirthday(resultSet.getDate("birthday"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return student;
    }

    public Student save(Student student){
        boolean educationForm = true;

        if(student.getEducationForm().equals("PAID")){
            educationForm = false;
        }

        try {
            PreparedStatement getGroupIdSet =
                    connection.prepareStatement("SELECT id FROM groups WHERE group_num=?");

            getGroupIdSet.setInt(1, student.getGroupNumber());

            ResultSet resultSet = getGroupIdSet.executeQuery();
            resultSet.next();

            int id = resultSet.getInt("id");
            getGroupIdSet.close();


            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO students VALUES(?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(    1, student.getStudentID());
            preparedStatement.setString( 2, student.getName());
            preparedStatement.setString( 3, student.getSecondName());
            preparedStatement.setString( 4, student.getPatronymic());
            preparedStatement.setBoolean(5, educationForm);
            preparedStatement.setInt(    6, id);
            preparedStatement.setDate(   7, student.getBirthday());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return student;
    }

    public void update(int studentID, Student updatedStudent) {

        boolean educationForm = true;

        if(updatedStudent.getEducationForm().equals("PAID")){
            educationForm = false;
        }

        try {

            PreparedStatement getGroupIdStatement =
                    connection.prepareStatement("SELECT id FROM groups WHERE group_num=?");

            getGroupIdStatement.setInt(1, updatedStudent.getGroupNumber());
            ResultSet resultSet = getGroupIdStatement.executeQuery();
            resultSet.next();

            int id = resultSet.getInt("id");
            getGroupIdStatement.close();

            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE students SET first_name=?, second_name=?, pantromyc=?, education_form=?, group_num=?, birthday=? WHERE student_id=?");

            //preparedStatement.setInt(1, updatedStudent.getStudentID());
            preparedStatement.setString(1, updatedStudent.getName());
            preparedStatement.setString(2, updatedStudent.getSecondName());
            preparedStatement.setString(3, updatedStudent.getPatronymic());
            preparedStatement.setBoolean(4, educationForm);
            preparedStatement.setInt(5, id);
            preparedStatement.setDate(6, updatedStudent.getBirthday());
            preparedStatement.setInt(7, studentID);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateGroupNumber(int groupNumber, int newGroupNumber) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE students SET group_num=? WHERE group_num=?");

            preparedStatement.setInt(1, newGroupNumber);
            preparedStatement.setInt(2, groupNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(int studentID){

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM students WHERE student_id=?");

            preparedStatement.setInt(1, studentID);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
