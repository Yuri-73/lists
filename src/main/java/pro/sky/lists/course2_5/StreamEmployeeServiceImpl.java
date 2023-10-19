package pro.sky.lists.course2_5;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class StreamEmployeeServiceImpl implements StreamEmployee {
    private final EmployeeService employeeService = new EmployeeServiceImpl(); //Создаю связь с указанным сервисом,
    // чтобы взять из него содержимое Листа persons. Заинжектил вручную. Не знаю, почему не работает
    // инжекция в автомате через конструктор! Надо бы спросить: как инжектить в атомате через контекст и бины!!!

    List<Employee> personsNew = employeeService.getPersons(1); //Не нашёл ничего лучше, чем взять содержимое Листа persons
    // из класса EmployeeServiceImpl через метод getPersons, который именно для этого в том сервисе создал. Через объектную нотацию ведь невоможно взять:
    // Лист не объект! Но что удивительно, этот метод не формируется, если в него не положить хоть какой-либо параметр, что я и сделал: послал ненужную 1.
    // Надо бы спросить: как другие ребята данные для этого Листа от другого Листа из другого класса брали!!!
    private static final int MAX_COUNT_EMPLOYEE = 8; //Константа-ограниченитель

    @Override
    public void addPersonNew(String firstName, String lastName, Integer departament, Integer salary) { //Метод ввода нового объекта в Лист.
        // По условию задания он не нужен. Сделал его первоначально, собираясь в Лист personsNew вносить элементы, а потом понял,
        // что их надо заносить по методу getPersons одним махом.
        if (personsNew.size() == MAX_COUNT_EMPLOYEE) {
            throw new EmployeeStorageIsFullException("Коллекция переполнена - превышен лимит сотрудников!");
        }
        int count = 0;
        Employee employee = new Employee(firstName, lastName, departament, salary);
        for (int i = 0; i < personsNew.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
            if ((personsNew.get(i).equals(employee))) { //Сравниваем параметр-объект с каждым из объектов коллекции-массива
                count++;
            }
        }
        if (count == 0)
            personsNew.add(employee);

        if (count != 0) {
            throw new EmployeeAlreadyAddedException("Уже есть такой!");
        }
    }
    @Override
    public List<Employee> listPersonNew() {
        return personsNew.stream().collect(Collectors.toList()); //Берётся наш Лист, оборачивается в стрим и далее в коллекцию через collect
    }
    @Override
    public String listPersonNewMod(int department1, int department2,int department3, int department4) { //Долго думал как выполнить последний пункт условия в ДЗ-2.8
        // и решил, что следует подавать в метод параметры 4-х отделов и через стримы создавать Листы для сотрудников каждого из отделов, а потом их сшить
        // в одну строку. И максимально по условию и возможность подавать параметры задом наперед. Надо бы спросить: правильно ли сделал!!!
        List<Employee> employeeListDep1 = new ArrayList<>(List.of());
        List<Employee> employeeListDep2 = new ArrayList<>(List.of());
        List<Employee> employeeListDep3 = new ArrayList<>(List.of());
        List<Employee> employeeListDep4 = new ArrayList<>(List.of());
        employeeListDep1 = personsNew.stream().filter(employee -> employee.getDepartment() == department1).collect(Collectors.toList());
        employeeListDep2 = personsNew.stream().filter(employee -> employee.getDepartment() == department2).collect(Collectors.toList());
        employeeListDep3 = personsNew.stream().filter(employee -> employee.getDepartment() == department3).collect(Collectors.toList());
        employeeListDep4 = personsNew.stream().filter(employee -> employee.getDepartment() == department4).collect(Collectors.toList());
        String a = "Отдел " + department1 + ": " + employeeListDep1.toString() +
                   " **** Отдел " + department2 + ": " + employeeListDep2.toString() +
                   " **** Отдел " + department3 + ": " + employeeListDep3.toString() +
                   " **** Отдел " + department4 + ": " + employeeListDep4.toString();

        return a;
    }
    @Override
    public Optional<Employee> maxSalaryDepartmentNew(int department) {
        Optional<Employee> employeeList = personsNew.stream().filter(employee -> employee.getDepartment() == department)
                .max(Comparator.comparingInt(employee -> employee.getSalary())); //Долго мудрил, но получилось, когда приравнял эту стримную строку
        // псевдоЛисту Optional и Optional также вывел на возврат. Ведь выводится один лишь элемент Листа, пусть даже и того же типа (объекта). Сначала наш Лист фильтруется по нужному отделу,
        // а потом уже находится элемент с максимальной зарплатой. Оборачивать в collect нельзя!
        return employeeList;
    }
    @Override
    public Optional<Employee> minSalaryDepartmentNew(Integer department) {
        Optional<Employee> employeeList = personsNew.stream().filter(employee -> employee.getDepartment() == department)
               .min(Comparator.comparingInt(employee -> employee.getSalary()));
        return employeeList;
    }
    @Override
    public List<Employee> DepartmentNew(Integer department) { //Ещё один метод со стримом: фильтруется нужный отдел и
        // выводится коллекция только сотрудников этого самого отдела.
        List<Employee> employeeList = new ArrayList<>(List.of());

        return personsNew.stream().filter(employee -> employee.getDepartment() == department).collect(Collectors.toList());
        //contains не подходит для сравнения целых чисел
    }
}
