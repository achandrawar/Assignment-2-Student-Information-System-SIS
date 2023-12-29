package entity;
import java.sql.*;
import java.util.List;

import util.DBUtil;

public class StudentEnrollment {
    private Connection connection;

    public StudentEnrollment() {
        this.connection = DBUtil.getDBConn();
    }


    public void createNewStudent(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        try {
            String insertStudentQuery = "INSERT INTO students (first_name, last_name, date_of_birth, email, phone_number) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setDate(3, dateOfBirth);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phoneNumber);

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int studentId = generatedKeys.getInt(1);
                        System.out.println("New student record created with ID: " + studentId);

                        enrollStudentInCourses(studentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enrollStudentInCourses(int studentId) {
        List<Integer> courseIds = List.of(1, 2);
        try {
            String insertEnrollmentQuery = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?,?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertEnrollmentQuery)) {
                for (int courseId : courseIds) {
                    preparedStatement.setInt(1, studentId);
                    preparedStatement.setInt(2, courseId);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                System.out.println("Student enrolled in courses.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
