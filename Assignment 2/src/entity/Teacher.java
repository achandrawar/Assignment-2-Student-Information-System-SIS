package entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.TeacherNotFoundException;
import util.DBUtil;

public class Teacher {
    Connection connection;
    {
        connection= DBUtil.getDBConn();
    }

    int teacherId;
    String firstName;
    String lastName;
    String email;
    String expertise;

    public Teacher(){}
    public Teacher(int teacherId, String firstName, String lastName, String email, String expertise) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.expertise = expertise;
    }
    public String getFirstName() 
	{
		
	return firstName;
	}
	public String getLastName() 
	
	{
		return lastName;
	}
	public int getTeacherID() {
		return teacherId;
	}

    public void updateTeacherInfo(int teacherId,String firstName,String lastName, String email, String expertise) {
        try {
            String updateQuery = "UPDATE teacher SET first_name=?, last_name=?, email=?, expertise=? WHERE teacher_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, expertise);
                preparedStatement.setInt(5, teacherId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTeacherInfo(int teacherId) {
        try {
            if(teacherId == 0){
                throw  new TeacherNotFoundException("Teacher details not found");
            }
            String selectQuery = "SELECT * FROM teacher WHERE teacher_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, teacherId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Teacher ID: " + resultSet.getInt("teacher_id"));
                        System.out.println("Name: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                        System.out.println("Email: " + resultSet.getString("email"));
                        System.out.println("Expertise: " + resultSet.getString("expertise"));
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }catch (SQLException e) {
            e.printStackTrace();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getAssignedCourses(int teacherId) {
        List<String> assignedCourses = new ArrayList<>();
        try{
            String selectQuery = "SELECT course_name FROM courses WHERE teacher_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, teacherId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        assignedCourses.add(resultSet.getString("course_name"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Object obj : assignedCourses){
            System.err.println(obj);
        }
    }
}
