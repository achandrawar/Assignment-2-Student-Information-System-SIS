package entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SIS {
    Scanner input = new Scanner(System.in);
    Student student = new Student();
    Teacher teacher = new Teacher();
    Course course = new Course();
    Payment payment = new Payment();
    Enrollment enrollment = new Enrollment();
    public SIS(){}

    public void enrollNewStudent(){
        System.out.println("Enter the details of the new student:");
        System.out.println("Enter the StudentID :");
        int studentID = input.nextInt();
        System.out.println("enter the Course ID : ");
        int courseId = input.nextInt();
        student.enrollInCourse(studentID,courseId); 
    }
    public void AssignTeacherToCourse(){
        System.out.println("Enter the details to assign Teacher to Course:");
        System.out.println("Enter the TeacherID");
        int tid=input.nextInt();
        System.out.println("Enter the Course ID : ");
        int cid = input.nextInt();
        Teacher t1 = new Teacher(tid,"","","","");
        course.AssignTeacher(t1,cid);
    }
    public void MakePayment(){
        System.out.println("Enter The Details Of Payment");
        System.out.println("Enter the StudentID :");
        int studentID = input.nextInt();
        System.out.println("Enter the Amount :");
        double amount = input.nextDouble();
        student.makePayment(studentID, amount);
    }
    public void generateEnrollmentReport(){
        EnrollmentReport enrollment = new EnrollmentReport();
        System.out.println("Enter the Course Name for Which You want to Generate Enrollment Report :");
        String cname = input.nextLine();
        enrollment.generateEnrollmentReport(cname);
    }
    public void GetEnrollmentHistory(){
        System.out.println("Enter the Course ID : ");
        int cid = input.nextInt();
        course.GetEnrollments(cid);
    }

    public void GeneratePaymentReport(){
        EnrollmentReport enrollment = new EnrollmentReport();
        Student s1 = new Student(4,"Ayush","",null,"","");
        enrollment.generatePaymentReport(s1);
    }

    public void displayInfo(){
        System.out.println("Enter the StudentID :");
        int studentID = input.nextInt();
        input.nextLine();
        System.out.println("Displaying Information:");
        student.displayStudentInfo(studentID);
    }
    public void enrollStudentInCourse(){
        System.out.println("\n\nEnrolling a New Student in a Course:\n");
        System.out.println("Enter the StudentID :");
        int studentID = input.nextInt();
        System.out.println("Enter the Course ID : ");
        int cid = input.nextInt();
        System.out.println("Enter the Enrollment ID : ");
        int eid = input.nextInt();
        enrollment.enrollStudentInCourse(studentID, cid, eid);
    }
    public void DynamicQuery(){
        DynamicQueryBuilder dynamicQueryBuilder = new DynamicQueryBuilder();
        System.out.println("Enter table name: ");
        String tableName = input.nextLine();

        System.out.println("Enter columns (comma-separated): ");
        String columnsInput = input.nextLine();
        List<String> columns = new ArrayList<>();
        if (!columnsInput.isEmpty()) {
            String[] columnArray = columnsInput.split(",");
            for (String column : columnArray) {
                columns.add(column.trim());
            }
        }

        System.out.println("Enter condition (optional): ");
        String condition = input.nextLine();
        System.out.println("Enter order by (optional): ");
        String orderBy = input.nextLine();

        List<Object[]> result = dynamicQueryBuilder.executeDynamicQuery(tableName, columns, condition, orderBy);
        for (Object[] row : result) {
            for (Object value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
}
