package pro.sky.lists.course2_5;

public class EmployeeStorageIsFullException extends RuntimeException {
    public EmployeeStorageIsFullException(String message) {
        super(message);
    }
}
