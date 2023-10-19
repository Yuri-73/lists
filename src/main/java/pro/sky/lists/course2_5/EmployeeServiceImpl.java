package pro.sky.lists.course2_5;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    List<Employee> persons = new ArrayList<>(List.of( //*Объявление и инициализация пополняемого списка (коллекции-массива)*
            new Employee("Жан", "Рено", 1, 75000),
            new Employee("Люк", "Бессон", 1, 80000),
            new Employee("Жерар", "Депардье", 2, 73000),
            new Employee("Джейсон", "Стетхем", 2, 91000),
            new Employee("Роберт", "Патрик", 3, 74000),
            new Employee("Олег", "Басилашвили", 3, 155000),
            new Employee("Владимир", "Машков", 4, 195000),
            new Employee("Павел", "Безруков", 4, 185000),
            new Employee("Андрей", "Соколов", 4, 135000)
            ));

    private static final int MAX_COUNT_EMPLOYEE = 8; //Константа-ограниченитель

    @Override
    public List<Employee> getPersons(int t) {
        List<Employee> person1 = new ArrayList<>(List.of());
        for (int i = 0; i < persons.size(); i++) {
            person1.add(persons.get(i));
        }
        return person1;
    }
    @Override
    public List<Employee> indexingSalaryDepartment(int department, int percent) {
        List<Employee> employeeList = new ArrayList<>(List.of());
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getDepartment() == department) {
                persons.get(i).setSalary(persons.get(i).getSalary() + (persons.get(i).getSalary() / 100) * percent);
                employeeList.add(persons.get(i));
            }
        }
        return employeeList;
    }

    @Override
    public String getPerson(Integer number) throws EmployeeStorageIsFullException{
        final Employee person; //Создаём внутреннюю ссылочную переменную, чтобы присвоить ей содержимое по индексу number
        if (number == null) {
            throw new EmployeeNotFoundException("Некорректный ввод параметра number!");
        }
        if (number == MAX_COUNT_EMPLOYEE) { //*Размер списка вышел на заданное ограничение*
            throw new EmployeeStorageIsFullException("Превышен ограничительный лимит списка!");
        }
        if (number > persons.size() - 1) { //*Размер списка вышел за свой текущий предел*
            throw new EmployeeStorageIsFullException("По данному номеру пока сотрудник не включён");
        }
        person = persons.get(number); //*Содержимое списка по индексу number*

        final String personDescription = "" + person.getFirstName() + " " + person.getLastName();
        return personDescription;
    }
    @Override
    public void addPerson(String firstName, String lastName, Integer departament, Integer salary) { //Метод ввода нового объекта в коллекцию-массив
        if (persons.size() == MAX_COUNT_EMPLOYEE) {
            throw new EmployeeStorageIsFullException("Коллекция переполнена - превышен лимит сотрудников!");
        }
        int count = 0;
        Employee employee = new Employee(firstName, lastName, departament, salary);
        for (int i = 0; i < persons.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
            if ((persons.get(i).equals(employee))) { //Сравниваем параметр-объект с каждым из объектов коллекции-массива
                count++;
            }
        }
        if (count == 0)
            persons.add(employee);

        if (count != 0) {
            throw new EmployeeAlreadyAddedException("Уже есть такой!");
        }

    }
    @Override
    public void removePerson(String firstName, String lastName) throws EmployeeNotFoundException { //Метод исключения объекта из коллекции-массива
        int count = 0;
        for (int i = 0; i < persons.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
            if ((persons.get(i).getFirstName()).equals(firstName) && (persons.get(i).getLastName()).equals(lastName)) { //Сравниваем параметр-объект с каждым из объектов коллекции-массива
                count = 1;
                persons.remove(persons.get(i));
            }
        }
        if (count == 0) {
            throw new EmployeeNotFoundException("Такой сотрудник не найден и поэтому нельзя удалить!");
        }
    }
    @Override
    public Employee findPerson(String firstName, String lastName) {  //Поиск человека по имени и фамилии
        int count = 0;
        for (int i = 0; i < persons.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
            Employee employee;
            if ((persons.get(i).getFirstName()).equals(firstName) && (persons.get(i).getLastName()).equals(lastName)) {
                employee = persons.get(i); //Собирание локального объекта по его входным свойствам
                count++;
                return employee;
            }
        }
        if (count != 0) {
            throw new EmployeeNotFoundException("Такой сотрудник отсутствует");
        }
        if (firstName == null || lastName == null) { //Особо следует взять на заметку, что генерацию ошибки, связанную с null-параметром, следует распологать раньше других генераций ошибок,
            // потому что в другой генерации возможен будет вызов метода для этого нулевого параметра, что приведёт к выбросу исключения (в браузере появится статус=500).
            // Например, моё исключение EmployeeNotFoundException наверняка этим методом пользуется, потому что выбрасывается ошибка (если это исключение, конечно,
            // переставить раньше исключения EmployeeAlreadyAddedException!)
            throw new EmployeeAlreadyAddedException("Ввод некорректного параметра");
        }

        return persons.get(0);
    }
    @Override
    public String listPerson() {
        return persons.toString();
    }
    @Override
    public int amountOfSalariesForMonth() {
        int sum = 0;
        int count = 0; //Переменная счёта, которая будет исключать нулевые зарплаты в вакансиях
        int averageSalary;
        for (int i = 0; i < persons.size(); i++) {
            sum = sum + persons.get(i).getSalary();
            count++;
            }
        averageSalary = sum / count;
        return averageSalary;
    }
    @Override
    public int minimumWage() {
        int min = Integer.MAX_VALUE; //Самое максимальное число, чтобы его уменьшать
        int x = 0;
        for (int i = 0; i < persons.size(); i++) {
            if (min > persons.get(i).getSalary()) {
                min = persons.get(i).getSalary();
                x = i;
            }
        }
        return min;
    }
    @Override
    public int maximumWage() {
        int max = Integer.MIN_VALUE; //Самое минимальное число, чтобы его увеличивать
        int x = 0;
        for (int i = 0; i < persons.size(); i++) {
            if (max < persons.get(i).getSalary()) {
                max = persons.get(i).getSalary();
                x = i;
            }
        }
        return max;
    }
    @Override
    public void indexingAllSalary(int percent) {
        for (int i = 0; i < persons.size(); i++) {
            persons.get(i).setSalary(persons.get(i).getSalary() + (persons.get(i).getSalary() / 100) * percent);
        }
    }
    @Override
    public List<Employee> listDepartment(int department) {
        List<Employee> employeeList = new ArrayList<>(List.of());
        for (int i = 0; i < persons.size(); i++) {
            if(persons.get(i).getDepartment() == department) //Поиск всех линейных массивов одного выбранного отдела
                employeeList.add(persons.get(i));
        }
        return employeeList;
    }
    @Override
    public int maxSalaryDepartment(int department) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getDepartment() == department)
                if (max < persons.get(i).getSalary()) {
                    max = persons.get(i).getSalary();
                }
        }
        return max;
    }
    @Override
    public int minSalaryDepartment(int department) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getDepartment() == department)
                if (min > persons.get(i).getSalary()) {
                    min = persons.get(i).getSalary();
                }
        }
        return min;
    }




}







