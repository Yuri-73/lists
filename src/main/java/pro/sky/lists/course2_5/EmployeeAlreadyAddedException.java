package pro.sky.lists.course2_5;

public class EmployeeAlreadyAddedException extends RuntimeException {
    public EmployeeAlreadyAddedException(String message) {
        super(message);
    }
}
