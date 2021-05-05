package com.netcracker.lab3webApp.dao;

import com.netcracker.lab3webApp.models.Group;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDAO {

    private static Connection connection = ConnectionInitializer.getConnection();

    public List<Integer> getGroupsNums(){

        List<Integer> groupsNums = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT group_num FROM groups";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                Integer groupNum = resultSet.getInt("group_num");

                groupsNums.add(groupNum);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return groupsNums;
    }

    public List<Group> index() {
        List<Group> groups = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM groups";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Group group = new Group();

                group.setGroupNumber(resultSet.getInt("group_num"));

                boolean form = resultSet.getBoolean("education_form");
                if(form){
                    group.setEducationForm("FULL-TIME");
                }
                else {
                    group.setEducationForm("PART-TIME");
                }

                groups.add(group);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return groups;
    }

    public Group show(int groupNumber) {
        Group group = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM groups WHERE group_num=?");

            preparedStatement.setInt(1, groupNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            group = new Group();
            group.setGroupNumber(resultSet.getInt("group_num"));
            boolean form = resultSet.getBoolean("education_form");
            if(form){
                group.setEducationForm("FULL-TIME");
            }
            else {
                group.setEducationForm("PART-TIME");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return group;
    }

    public void save(Group group) {
        boolean form = true;

        if(group.getEducationForm().equals("PART-TIME")) {
            form = false;
        }

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO groups VALUES (?, ?)");

            preparedStatement.setInt(1, group.getGroupNumber());
            preparedStatement.setBoolean(2, form);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int groupNumber, Group updatedGroup) {
        boolean form = true;

        if(updatedGroup.getEducationForm().equals("PART-TIME")) {
            form = false;
        }
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE groups SET group_num=?, education_form=? WHERE group_num=?");

            preparedStatement.setInt(1,updatedGroup.getGroupNumber());
            preparedStatement.setBoolean(2, form);
            preparedStatement.setInt(3, groupNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int groupNumber){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM groups WHERE group_num=?");

            preparedStatement.setInt(1, groupNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
