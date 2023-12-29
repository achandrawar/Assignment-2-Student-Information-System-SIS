package entity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidStudentDataException;
import exceptions.PaymentValidationException;
import exceptions.StudentNotFoundException;
import util.DBUtil;

public class Student {
    int studentId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    Connection connection;
    {
        connection= DBUtil.getDBConn();
    }
    public Student(){}
    
    public Student(int studentId, String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;    
    }

    public void enrollInCourse(int studentId, int courseId){
        try{
            if(studentId == 0){
                throw new StudentNotFoundException("Student Not Found");
            }
            String insertQuery = "INSERT INTO enrollments ( student_id, course_id, enrollment_date) VALUES ( ?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);
                preparedStatement.executeUpdate();
                System.out.println("Student Enrolled in Course with CourseID : "+ courseId);
            }catch(SQLException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
 
    public void updateStudentInfo(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        try {
            if(studentId == 0){
                throw new InvalidStudentDataException("Invalid Details Entered");
            }
            String updateQuery = "UPDATE students SET first_name=?, last_name=?, date_of_birth=?, email=?, phone_number=? WHERE student_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setDate(3, new java.sql.Date(dateOfBirth.getTime()));
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phoneNumber);
                preparedStatement.setInt(6, studentId);
                preparedStatement.executeUpdate();
                System.out.println("Student Information Updated with deatils : \n" + "Name : " + firstName +" " + lastName + "\n"+
                "DOB : " + dateOfBirth + "\n" + "Emaik : " + email + "\n" + "Phone : " + phoneNumber );
            }catch(SQLException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makePayment(int studentID,double amount) {
        try{
            if(amount < 0){
                throw new PaymentValidationException("Invalid Payment Details");
            }
            String insertQuery = "INSERT INTO payments (student_id, amount, payment_date) VALUES (?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, studentID);
                preparedStatement.setDouble(2, amount);
                preparedStatement.executeUpdate();
                System.out.println("Payment Successfull");
            }catch(SQLException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayStudentInfo(int studentId) {
        try{
            String selectQuery = "SELECT * FROM students WHERE student_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int retrievedStudentId = resultSet.getInt("student_id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        Date dateOfBirth = resultSet.getDate("date_of_birth");
                        String email = resultSet.getString("email");
                        String phoneNumber = resultSet.getString("phone_number");

                        System.out.println("Student ID: " + retrievedStudentId);
                        System.out.println("First Name: " + firstName);
                        System.out.println("Last Name: " + lastName);
                        System.out.println("Date of Birth: " + dateOfBirth);
                        System.out.println("Email: " + email);
                        System.out.println("Phone Number: " + phoneNumber);
                    } else {
                        System.out.println("Student not found with ID: " + studentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getEnrolledCourses() {
        List<Integer> enrolledCourses = new ArrayList<>();
        try{
            String selectQuery = "SELECT course_id FROM enrollments WHERE student_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        enrolledCourses.add(resultSet.getInt("course_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(enrolledCourses.size() == 0){
            System.out.println("Student with StudentID : " + studentId + " is enrolled in 0 Courses");
        }else{
        System.out.println("Student with StudentID : " + studentId + " is enrolled in Courses with course ID : " + enrolledCourses);
        }
    }

    public void getPaymentHistory(int studentId) {
        List<Double> paymentHistory = new ArrayList<>();
        try {
            String selectQuery = "SELECT amount FROM payments WHERE student_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        paymentHistory.add(resultSet.getDouble("amount"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double s=0;
        for(double i:paymentHistory){
            s += i;
        }
       System.out.println("Student with StudentID : " + studentId + " had made payment of : " + s);
    }
    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
     
}