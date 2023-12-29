package exceptions;

public class CourseNotFoundException extends Exception {
    String message = "";
    public CourseNotFoundException(String message) {
        // super(message);
        this.message = message;
    }

    @Override
    public String toString() {
           return message; 
    }
}