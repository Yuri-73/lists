package pro.sky.lists.course2_5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/streamPerson") //Вывод общего для всех методов адреса-строки как общего множителя
public class StreamController {  //В этом классе написаны методы для взаимодействия со своим сервис-классом. Более подробно описаны действия
    // в этом самом сервис-классе - streamEmployeeImpl
    private final StreamEmployee streamEmployee;

    public StreamController(StreamEmployee streamEmployee) {
        this.streamEmployee = streamEmployee;
    }

    @GetMapping(path = "/addMod") //Получает по указанному адресу в адресной строке поля объекта: name, surname, departament и salary
    //localhost:8080/streamPerson/addMod?name=Владимир&surname=Гуляницкий&departament=1&salary=28000
    public String addPersonNew(
            @RequestParam(required = false) String name, //false для того прописываю, чтобы система не давала ошибку при некорректном вводе параметра и её
            //null можно было оценить в операторе if в вызываемом методе в классе-сервисеИмпл
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer departament,
            @RequestParam(required = false) Integer salary) {

        try {
            streamEmployee.addPersonNew(name, surname, departament, salary); //Вызов соответствующего метода в классе сервиса(импл). Тот ничего не отдаёт наружу.
            return "Сотрудник " + name + " " + surname + " вошёл в список"; //Достаточно лишь только имени и фамилии для информации.
        } catch (EmployeeStorageIsFullException | EmployeeAlreadyAddedException e) {
            return e.getMessage(); //Вот эта хитрая вещь даёт возможность передать в браузер строку ошибки, написанную в соотв. методе сервиса(импл)
        }
    }
    @GetMapping(path = "/departments")
    //localhost:8080/streamPerson/departments
    public List<Employee> listPersonNew() {
        return streamEmployee.listPersonNew();
    } //Вызываем и тут же получаем результат, в котором распечатывается вся коллекция целиком за счёт toString в классе Employee

    @GetMapping(path = "/allDepartments") //Распечатка коллекции в соответствии с последним пунктом ДЗ-2.8, т.е через стрим (подробно в сервис-классе)
    //localhost:8080/streamPerson/allDepartments?departmentId1=4&departmentId2=3&departmentId3=2&departmentId4=1
    public String listPersonNewMod(@RequestParam(required = true) Integer departmentId1, @RequestParam(required = true) Integer departmentId2,
                                   @RequestParam(required = true) Integer departmentId3, @RequestParam(required = true) Integer departmentId4) {
        return streamEmployee.listPersonNewMod(departmentId1, departmentId2,departmentId3, departmentId4);
    }

     @GetMapping(path = "/departments/max-salary") //Распечатка максимальной зарплаты сотрудника определённого отдела
    //localhost:8080/streamPerson/departments/max-salary?departmentId=1
    public String maxSalaryDepartmentNew(@RequestParam(required = true) Integer departmentId) {
         return "Сотрудник с максимальной зарплатой в отделе " + departmentId + ": " + streamEmployee.maxSalaryDepartmentNew(departmentId);
    }

    @GetMapping(path = "/departments/min-salary") //Распечатка минимальной зарплаты сотрудника определённого отдела
    //localhost:8080/streamPerson/departments/min-salary?departmentId=1
    public String minSalaryDepartmentNew(@RequestParam(required = true) Integer departmentId) {

        return "Сотрудник с минимальной зарплатой в отделе " + departmentId + ": " + streamEmployee.minSalaryDepartmentNew(departmentId);
    }

    @GetMapping(path = "/departments/all") //Распечатка сотрудников определённого отдела
    //localhost:8080/streamPerson/departments/all?departmentId=1
    public String DepartmentNew(@RequestParam(required = true) Integer departmentId) {
        return "Список сотрудников отдела " + departmentId + ": " + streamEmployee.DepartmentNew(departmentId);
    }
}
