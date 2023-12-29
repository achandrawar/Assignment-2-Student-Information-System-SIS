package entity;
import java.sql.*;
import util.DBUtil;

public class EnrollmentReport {
    private Connection connection;

    public EnrollmentReport() {
        this.connection = DBUtil.getDBConn();
    }

    public void generateEnrollmentReport(String courseName) {
        try {
            ResultSet resultSet = getEnrollmentRecords(courseName);
            generateAndDisplayReport(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getEnrollmentRecords(String courseName) throws SQLException {
        String selectQuery = "SELECT students.*, enrollments.enrollment_date FROM students " +
                "JOIN enrollments ON students.student_id = enrollments.student_id " +
                "JOIN courses ON enrollments.course_id = courses.course_id " +
                "WHERE courses.course_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, courseName);
        return preparedStatement.executeQuery();
    }

    private void generateAndDisplayReport(ResultSet resultSet) throws SQLException {
        System.out.println("Enrollment Report:");
        System.out.println("=================================");
        System.out.printf("| %-5s | %-15s | %-15s | %-10s | %-30s | %-15s |%n",
                "ID", "First Name", "Last Name", "DOB", "Email", "Enrollment Date");
        System.out.println("=================================");

        while (resultSet.next()) {
            int studentId = resultSet.getInt("student_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date dateOfBirth = resultSet.getDate("date_of_birth");
            String email = resultSet.getString("email");
            String enrollmentDate = resultSet.getString("enrollment_date");

            System.out.printf("| %-5d | %-15s | %-15s | %-10s | %-30s | %-15s |%n",
                    studentId, firstName, lastName, dateOfBirth, email, enrollmentDate);
        }

        System.out.println("=================================");
    }


    

    public void generatePaymentReport(Student student) {
        try {
            ResultSet resultSet = getPaymentRecords(student.getStudentId());
            generateAndDisplayPaymentReport(student, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getPaymentRecords(int studentId) throws SQLException {
        String selectQuery = "SELECT * FROM payments WHERE student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, studentId);
        return preparedStatement.executeQuery();
    }

    private void generateAndDisplayPaymentReport(Student student, ResultSet resultSet) throws SQLException {
        System.out.println("Payment Report for StudentID: "+ student.getStudentId());
        System.out.println("=================================");
        System.out.printf("| %-5s | %-10s | %-10s | %-20s |%n", "ID", "Amount", "Payment Date", "Course Name");
        System.out.println("=================================");

        while (resultSet.next()) {
            int paymentId = resultSet.getInt("payment_id");
            double amount = resultSet.getDouble("amount");
            Date paymentDate = resultSet.getDate("payment_date");
            String courseName = getCourseNameForPayment(resultSet.getInt("student_id"));

            System.out.printf("| %-5d | $%-9.2f | %-15s | %-20s |%n",
                    paymentId, amount, paymentDate, courseName);
        }

        System.out.println("=================================");
    }

    private String getCourseNameForPayment(int studentId) throws SQLException {
        String selectQuery = "SELECT courses.course_name FROM courses " +
                "JOIN enrollments ON courses.course_id = enrollments.course_id " +
                "WHERE enrollments.student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, studentId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("course_name");
        }

        return "N/A";
    }


}

