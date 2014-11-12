package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Employee;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovy on 11/11/2014.
 */
@MyController(urlPath="/employee")

public class EmployeeController {
@MyRequestMethod(urlPath="/all")
    public List<Employee> getAllEmployees(){

   List<Employee> l = new ArrayList<Employee>();
   /* Employee e = new Employee();
    e.setId(16l);
    e.setFirstName("Mihai");

    Employee e1 = new Employee();
    e1.setId(17l);
    e1.setFirstName("Mihaai");

    Employee e2 = new Employee();
    e2.setId(19l);
    e2.setFirstName("Mihaai");
    l.add(e);
    l.add(e1);
    l.add(e2);*/
    EmployeeServiceImpl n = new EmployeeServiceImpl();
    l=n.findAllEmployees();
    return l;

    }
    @MyRequestMethod(urlPath ="/one")
    public Employee getOneEmployee(String id)
    {


        System.out.println("ceva");
        EmployeeServiceImpl n = new EmployeeServiceImpl();
        Employee emp = n.findOneEmployee(Long.parseLong(id));

        return emp;
    }
}
