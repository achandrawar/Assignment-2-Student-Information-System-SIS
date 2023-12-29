package entity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exceptions.CourseNotFoundException;
import util.DBUtil;

public class Course {
    private int courseID;
    private String courseName;
    int teacherID;
    List<Enrollment> enrollments;
    int credits;
    Connection connection;
    {
        connection= DBUtil.getDBConn();
    }
    public Course(){}
    public Course(int courseID, String courseName, int teacherID, int credits) 
    {
        this.courseID = courseID;
        this.courseName = courseName;	
        this.enrollments = new ArrayList<>();
        this.credits = credits;
        this.teacherID= teacherID;
    }
    public int getCourseId() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public int getTeacherId() {
        return teacherID;
    }

    public void AssignTeacher(Teacher teacher, int CourseID){
        String sql = "UPDATE courses SET teacher_id = ? WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacher.getTeacherID());
            statement.setInt(2, courseID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher " + teacher.getFirstName() + " assigned to course ID: " + courseID);
            } else {
                System.out.println("Failed to assign teacher to course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    //UpdateCourseInfo method using JDBC
    public void UpdateCourseInfo(int courseID,String courseName) throws CourseNotFoundException {
        try{if(courseID == 0){
            throw new CourseNotFoundException("Course With CourseID " + courseID + " is Not Found");
        }else{
        String sql = "UPDATE courses SET course_name = ? WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName); 
            statement.setInt(2, courseID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                this.courseName = courseName;
                System.out.println("Course information updated: " + courseName);
            } 
            else 
            {
                System.out.println("Failed to update course information.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            }
        }
    }catch(Exception e ){
        e.printStackTrace();
        }
    }


    // Method 3 DisplayCourseInfo method using JDBC
    public void DisplayCourseInfo(Teacher teacher){   
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, teacher.getTeacherID());
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    System.out.println("Assigned Teacher :  " + firstName + " " + lastName);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Method (4) GetEnrollments method using JDBC
    public void GetEnrollments(int courseID) {
        List<Enrollment> enrollments = new ArrayList<>();
        Enrollment enrollment = null;
        try{
            String sql = "SELECT * FROM Enrollments JOIN Students ON Enrollments.student_id = Students.student_id JOIN Courses ON Enrollments.course_id = Courses.course_id WHERE Courses.course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql); 
            statement.setInt(1, courseID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int enrollmentID = resultSet.getInt("enrollment_id");
                    int studentID = resultSet.getInt("student_id");
                    Date date = resultSet.getDate("enrollment_date");
                    enrollment = new Enrollment(enrollmentID, studentID, courseID,date);
                    enrollments.add(enrollment);
                    for(Enrollment i: enrollments) {
                        System.out.println(i);
                    }
                }
            }catch(SQLException e) {
                     e.printStackTrace();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }             
    }



    public void GetTeacher(Teacher teacher){
        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacher.getTeacherID());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {  
                    int teacherID = resultSet.getInt("teacher_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);    
                    System.out.println("Teacher ID: " + teacherID);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    
