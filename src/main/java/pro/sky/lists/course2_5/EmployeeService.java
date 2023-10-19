package pro.sky.lists.course2_5;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getPersons(int t);

    String getPerson(Integer number) throws EmployeeStorageIsFullException;

    void addPerson(String firstName, String lastName, Integer departament, Integer salary);

    void removePerson(String firstName, String lastName);

    Employee findPerson(String firstName, String lastName);

    String listPerson();

    int amountOfSalariesForMonth();

    int minimumWage();

    int maximumWage();

    void indexingAllSalary(int percent);

    List<Employee> listDepartment(int department);

    int maxSalaryDepartment(int department);

    int minSalaryDepartment(int department);


    public List<Employee> indexingSalaryDepartment(int department, int percent);

}
