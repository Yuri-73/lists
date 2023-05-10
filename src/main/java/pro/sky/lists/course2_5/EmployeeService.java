package pro.sky.lists.course2_5;

public interface EmployeeService {
    String getPerson(Integer number) throws EmployeeStorageIsFullException;


    void addPerson(Employee person);

    void removePerson(Employee person);


    Employee findPerson(String firstName, String lastName);

    String listPerson();
}
