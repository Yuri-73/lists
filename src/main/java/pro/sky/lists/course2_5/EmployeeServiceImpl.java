package pro.sky.lists.course2_5;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

    @Service
    public class EmployeeServiceImpl implements EmployeeService {
        List<Employee> persons = new ArrayList<>(List.of( //*Объявление и инициализация списка (коллекции-массива)*
                new Employee("Жан", "Рено"),
                new Employee("Люк", "Бессон"),
                new Employee("Жерар", "Депардье"),
                new Employee("Джейсон", "Стетхем"),
                new Employee("Роберт", "Патрик")));
        private static final int MAX_COUNT_EMPLOYEE = 6; //Константа-ограниченитель

        @Override
        public String getPerson(Integer number) throws EmployeeStorageIsFullException{
            final Employee person;
            if (number == null) {
            throw new EmployeeNotFoundException("Некорректный ввод параметра number!");
        }
            if (number == MAX_COUNT_EMPLOYEE) { //*Размер массива списка*
                throw new EmployeeStorageIsFullException("Превышен лимит списка!");
            }
            if (number > persons.size() - 1) { //*Размер массива списка*
                throw new EmployeeStorageIsFullException("По данному номеру пока сотрудник не включён");
            }
            person = persons.get(number); //*Содержимое списка по индексу number*

            final String personDescription = "" + person.getFirstName() + " " + person.getLastName();
            return personDescription;
        }
        @Override
        public void addPerson(Employee person) { //Метод ввода объекта в коллекцию-массив
            if (persons.size() == MAX_COUNT_EMPLOYEE) {
                throw new EmployeeStorageIsFullException("Коллекция переполнена - превышен лимит сотрудников!");
            }
            int count = 0;
            for (int i = 0; i < persons.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
                if (persons.get(i).equals(person)) { //Сравниваем параметр-объект с каждым из объектов коллекции-массива
                    count = 1;
                }
            }
            if (count == 1) {
                throw new EmployeeAlreadyAddedException("Уже есть такой!");
            }
            persons.add(person); //Вводим новый объект в коллекцию-массив
        }
        @Override
        public void removePerson(Employee person) { //Метод исключчения объекта из коллекции-массива
            int count = 0;
            for (int i = 0; i < persons.size(); i++) { //Итерация до последнего объекта в коллекции-массиве включительно
                if (persons.get(i).equals(person)) { //Сравниваем параметр-объект с каждым из объектов коллекции-массива
                    count = 1;
                }
            }
            if (count == 0) {
                throw new EmployeeNotFoundException("Такой сотрудник не найден и поэтому нельзя удалить!");
            }
            persons.remove(person);
        }
        @Override
        public Employee findPerson(String firstName, String lastName) {
            Employee employee = new Employee(firstName, lastName);

            if (!persons.contains(employee)) {
                throw new EmployeeNotFoundException("Такой сотрудник отсутствует");
            }
            return employee;
        }
        @Override
        public String listPerson() {
            return persons.toString();
        }

    }






