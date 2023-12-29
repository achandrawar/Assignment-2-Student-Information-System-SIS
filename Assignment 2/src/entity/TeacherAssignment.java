package entity;
import java.sql.*;
import util.DBUtil;

public class TeacherAssignment {
    private Connection connection;

    public TeacherAssignment() {
        this.connection = DBUtil.getDBConn();
    }

    public void assignTeacherToCourse(String courseCode, String teacherName, String teacherEmail, String expertise) {
        try {
            updateCourseRecord(courseCode, teacherName);
            System.out.println("Teacher assigned to the course.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourseRecord(String courseCode, String instructorName) throws SQLException {
        String updateQuery = "UPDATE courses SET instructor_name=? WHERE course_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, instructorName);
            preparedStatement.setString(2, courseCode);
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        TeacherAssignment teacherAssignment = new TeacherAssignment();

        // Example usage
        teacherAssignment.assignTeacherToCourse("CS302", "Sarah Smith", "sarah.smith@example.com", "Computer Science");
    }
}
