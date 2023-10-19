package pro.sky.lists.course2_5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person") //Вывод общего для всех методов адреса-строки как общего множителя
public class Controller { //Это ДЗ для урока 2.8. Оно наложилось на урок 2.5, т.к. это требование условия ДЗ. Отличия в том,
    // что в 2.5 было всего 2 поля: имени и фамилии, а для работы в ДЗ-2.8 надо добавить ещё отдел и зарплату в шаблон объекта - класс Employee,
    // а значит, добавить в сервисный класс EmployeeServiceImpl методы по работе с ними. Эти методы брались с контрольной работы 1-го курса,
    // т.к. изначально работа со списками сотрудников создавалась там. Осталось лишь адаптировать методы к стринг-буту. Но это лишь прилюдия ДЗ-2.8.
    // Далее надо создать нам новый сервис StreamEmployeeServiceImpl и контроллер к нему StreamController,
    // где начинка из некоторых этих самых методов переписывалась на стримы (всё согласно условию ДЗ-2.8)
    private final EmployeeService employeeService; //Объявили ссылочную переменную для связи с одноимённым сервисом

    public Controller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    } //Заинжектили через S-B

    @GetMapping(path = "")
    //localhost:8080/person?number=(0 или более)
    public String GetPersonInfo(@RequestParam(required = false) Integer number) { //(required = false) необходимо, чтобы компиллятор мог выявлять null
        //Нахождение объекта в Листе по номеру в динамическом массиве
        try {
            final String person; //Строка подскока
            person = employeeService.getPerson(number);
            return "Сотрудник " + person + " выявлен в составе списка";
        }
        catch (EmployeeStorageIsFullException e) {
            return e.getMessage();
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        } finally { //Закидывает информацию в консоль стринга, а не в браузер!
            System.out.println("Работа метода GetPersonInfo закончена");
        }

    }

    @GetMapping(path = "/add") //Получает по указанному адресу в адресной строке парметры для полей нового объекта: name, surname, departament и salary
    //localhost:8080/person/add?name=Виктор&surname=Христенко&departament=1&salary=12000
    public String addPerson(
            @RequestParam(required = false) String name, //false для того прописываю, чтобы система не давала ошибку при некорректном вводе параметра и её
            //null можно было оценить в операторе if в вызываемом методе в классе-сервисеИмпл
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer departament,
            @RequestParam(required = false) Integer salary) {

        try {
            employeeService.addPerson(name, surname, departament, salary); //Вызов соответствующего метода в классе сервиса(импл). Он ничего не отдаёт наружу.
            return "Сотрудник " + name + " " + surname + " вошёл в список";
        } catch (EmployeeStorageIsFullException | EmployeeAlreadyAddedException e) {
            return e.getMessage(); //Вот эта хитрая вещь даёт возможность передать в браузер строку ошибки, написанную в соотв. методе сервиса(импл)
        } finally {
            System.out.println("Работа метода GetPersonInfo закончена");
        }
    }

    @GetMapping(path = "/remove")
    //localhost:8080/person/remove?name=Жан&surname=Рено
    public String removePerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) {

        try {
            employeeService.removePerson(name, surname); //Достаточно только имени и фамилии у объекта
            return "Сотрудник " + name + " " + surname + " из списка удалён";
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/find")
    //localhost:8080/person/find?name=Жан&surname=Рено
    public String findPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) { //Достаточно для поиска всего 2-х полей

        try {
            return "Сотрудник: " + employeeService.findPerson(name, surname) + " имеется в списке";
        } catch (EmployeeAlreadyAddedException | EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/list")
    //localhost:8080/person/list
    public String listAllEmployee() {
        return employeeService.listPerson();
    } //Вызываем и тут же получаем результат,
    //в котором распечатывается вся коллекция целиком за счёт toString в классе Employee

    @GetMapping(path = "/averageSalary")
    //localhost:8080/person/averageSalary
    public String amountOfSalariesForMonth() {
        return "Средняя зарплата всех сотрудников составляет " + employeeService.amountOfSalariesForMonth() + " рублей";
    }
    @GetMapping(path = "/minSalary")
    //localhost:8080/person/minSalary
    public String minimumWage() {
        return "Минимальная зарплата из всего списка сотрудников составляет " + employeeService.minimumWage() + " рублей";
    }
    @GetMapping(path = "/maxSalary")
    //localhost:8080/person/maxSalary
    public String maximumWage() {
        return "Максимальная зарплата из всего списка сотрудников составляет " + employeeService.maximumWage() + " рублей";
    }
    @GetMapping(path = "/enteringIndex")
    //localhost:8080/person/enteringIndex?percent=10
    public String indexingAllSalary(@RequestParam(required = true) Integer percent) {
        employeeService.indexingAllSalary(percent);
        return "Список сотрудников всех отделов после общей индексации на " + percent + " процентов: " + employeeService.listPerson();
    }
    @GetMapping(path = "/departamentList")
    //localhost:8080/person/departamentList?departament=1
    public String listDepartment(@RequestParam(required = true) Integer departament) {
        return "Список сотрудников отдела " + departament + ": " + employeeService.listDepartment(departament);
    }
    @GetMapping(path = "/maxDep")
    //localhost:8080/person/maxDep?departament=0
    public String maxSalaryDepartment(@RequestParam(required = true) Integer departament) {
        return "Максимальная зарплата в отделе " + departament + " составляет: " + employeeService.maxSalaryDepartment(departament);
    }
    @GetMapping(path = "/minDep")
    //localhost:8080/person/minDep?departament=2
    public String minSalaryDepartment(@RequestParam(required = true) Integer departament) {
        return "Минимальная зарплата в отделе " + departament + " составляет: " + employeeService.minSalaryDepartment(departament);
    }
    @GetMapping(path = "/departamentIndex")
    //localhost:8080/person/departamentIndex?departament=1&percent=20
    public String indexingSalaryDepartment(@RequestParam(required = true) Integer departament, @RequestParam(required = true) Integer percent) {
        return "Список сотрудников отдела " + departament + " после индексации на " + percent + " процентов: " + employeeService.indexingSalaryDepartment(departament, percent);
    }

}
