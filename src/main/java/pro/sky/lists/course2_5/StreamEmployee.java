package pro.sky.lists.course2_5;


import java.util.List;
import java.util.Optional;

public interface StreamEmployee {
    void addPersonNew(String firstName, String lastName, Integer departament, Integer salary);


    List<Employee> listPersonNew();

    String listPersonNewMod(int department1, int department2,int department3, int department4);

    Optional<Employee> maxSalaryDepartmentNew(int department);

    Optional<Employee> minSalaryDepartmentNew(Integer department);

    List<Employee> DepartmentNew(Integer department);
}
