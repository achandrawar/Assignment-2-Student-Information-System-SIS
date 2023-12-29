package entity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DuplicateEnrollmentException;
import util.DBUtil;


public class Enrollment {
    Connection connection;
    {
        connection= DBUtil.getDBConn();
    }

    int enrollmentId;
    int studentId;
    int courseId;
    Date enrollmentDate;
    public Enrollment(){}
    public Enrollment(int enrollmentId, int studentId, int courseId, Date enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public void getStudent(int enrollmentId) {
        Student student = null;
        try {
            String selectQuery = "SELECT * FROM students s join enrollments e on s.student_id=e.student_id where enrollment_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, enrollmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                            student = new Student(
                            resultSet.getInt("student_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getDate("date_of_birth"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("First Name: " + student.getFirstName());
        System.out.println("Last Name: " + student.getLastName());
        System.out.println("Date of Birth: " + student.getDateOfBirth());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone Number: " + student.getPhoneNumber());
    }

    public void getCourse(int enrollmentId) {
       Course course = null;
        try {
            String selectQuery = "SELECT * FROM courses c join enrollments e on c.course_id= e.course_id where enrollment_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, enrollmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        course = new Course(resultSet.getInt("course_id"),
                            resultSet.getString("course_name"),
                            resultSet.getInt("credits"),
                            resultSet.getInt("teacher_id")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Course ID: " + course.getCourseId());
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Credit Hours: " + course.getCredits());
        System.out.println("Teacher ID: " + course.getTeacherId());
    }
    public String toString() {
        return "Enrollment Details : " +
                " EnrollmentId = " + enrollmentId +
                ", StudentId = " + studentId +
                ", CourseId = " + courseId +
                ", EnrollmentDate = " + enrollmentDate;
    }
    public void enrollStudentInCourse(int studentId, int courseId, int enrollmentId)  {
        try{
        if (isDuplicateEnrollment(enrollmentId)) {
            throw new DuplicateEnrollmentException("Student is already enrolled in the course.");
        }
        try{
            String insertQuery = "INSERT INTO enrollments (enrollment_id, student_id, course_id, enrollment_date) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, enrollmentId);
                preparedStatement.setInt(2, studentId);
                preparedStatement.setInt(3, courseId);
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
                }
        }catch(Exception e){
            e.printStackTrace();
        } 
    }catch(Exception e){
        e.printStackTrace();
        }
    }

    public boolean isDuplicateEnrollment(int enrollmentId) {
        String insertQuery = "SELECT enrollment_id FROM enrollments WHERE enrollment_id = ? AND enrollment_id NOT IN (SELECT enrollment_id FROM enrollments WHERE enrollment_id <> ?)";
        try{
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, enrollmentId);
                preparedStatement.setInt(2, enrollmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
