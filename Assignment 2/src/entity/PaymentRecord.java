package entity;
import java.sql.*;
import java.util.Date;

import exceptions.InvalidCourseDataException;
import util.DBUtil;

public class PaymentRecord {
    Connection connection;

    public PaymentRecord() {
        this.connection = DBUtil.getDBConn();
    }

    public void recordPayment(int studentId, double paymentAmount, Date paymentDate) {
        try {
            // Retrieve student record from the database
            getStudentById(studentId);

            // Record the payment information
            recordPaymentInDatabase(studentId, paymentAmount, paymentDate);
            if(paymentAmount >=2000){
                throw new InvalidCourseDataException("Insufficient Funds");
            }
            updateOutstandingBalance(studentId, paymentAmount);

            System.out.println("Payment recorded and outstanding balance updated for student with ID: " + studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStudentById(int studentId) throws SQLException {
        String selectQuery = "SELECT * FROM students WHERE student_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    // Date dateOfBirth = resultSet.getDate("date_of_birth");
                    // String email = resultSet.getString("email");
                    // String phoneNumber = resultSet.getString("phone_number");

                    System.out.println("Student details: ID: " + studentId + ", Name: " + firstName + " " + lastName);
                } else {
                    System.out.println("Student not found with ID: " + studentId);
                }
            }
        }
    }

    private void recordPaymentInDatabase(int studentId, double paymentAmount, Date paymentDate) throws SQLException {
        String insertQuery = "INSERT INTO payments (student_id, amount, payment_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setDouble(2, paymentAmount);
            preparedStatement.setDate(3, new java.sql.Date(paymentDate.getTime()));
            preparedStatement.executeUpdate();
            System.out.println("Payment recorded in the database.");
        }
    }

    private void updateOutstandingBalance(int studentId, double paymentAmount) throws SQLException {
        String updateQuery = "UPDATE students SET outstanding_balance = outstanding_balance - ? WHERE student_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
            System.out.println("Outstanding balance updated in the database.");
        }
    }

}
