package entity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBUtil;

public class Payment {
    int paymentId;
    Student student;
    double amount;
    Date paymentDate;
    Connection connection;
    {
        connection= DBUtil.getDBConn();
    }
    public Payment(){}

    public Payment(int paymentId, Student student, double amount, Date paymentDate) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    public void getStudent(int studentId) {
        try {
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
                    }else{
                        System.out.println("Student Details Not Found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void getPaymentAmount() {
        try {
            String selectQuery = "SELECT amount FROM payments WHERE payment_id=?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, paymentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Payment found with ID-"+paymentId+" is " + (int)resultSet.getDouble("amount"));
                    } else {
                        System.out.println("Payment record not found with ID-"+paymentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getPaymentDate() {
        try {
            String selectQuery = "SELECT payment_date FROM payments WHERE payment_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, paymentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Date paymentDate = resultSet.getDate("payment_date");
                        System.out.println("Payment Date for paymentID:"+paymentId+" = " + paymentDate);
                    } else {
                        System.out.println("Payment not found with ID: " + paymentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

}
