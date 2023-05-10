package pro.sky.lists.course2_5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/person")
public class Controller {
    private final EmployeeService employeeService;
    public Controller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(path = "")
    public String GetPersonInfo(@RequestParam(required = false) Integer number) { //(required = false) необходимо, чтобы компиллятор мог выявлять null
        try {
            final String person;
            person = employeeService.getPerson(number);
            return "Сотрудник " + person + " выявлен в составе списка";

        } catch (EmployeeStorageIsFullException e) {
            return e.getMessage();
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        } finally {
            System.out.println("Работа метода GetPersonInfo закончена");
        }
    }
    @GetMapping(path = "/add")
    public String addPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) {

        Employee person = new Employee(name, surname);
        try {
            employeeService.addPerson(person);
            return "Сотрудник " + person + " вошёл в список";
        } catch (EmployeeStorageIsFullException | EmployeeAlreadyAddedException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/remove")
    public String removePerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) {

        Employee person = new Employee(name, surname);
        try {
            employeeService.removePerson(person);
            return "Сотрудник " + person + " из списка удалён";
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }
    @GetMapping(path = "/find")
    public String findPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) {

        Employee person = new Employee(name, surname);
        try {
            employeeService.findPerson(name, surname);
            return "Сотрудник: " + person;
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }
    @GetMapping(path = "/list")
    public String listAllEmployee() {
        return employeeService.listPerson();
    }
}
