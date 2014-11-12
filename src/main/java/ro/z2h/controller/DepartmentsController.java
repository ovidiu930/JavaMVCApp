package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;
import ro.z2h.service.DepartmentServiceImpl;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovy on 11/11/2014.
 */
@MyController(urlPath="/department")
public class DepartmentsController {
    @MyRequestMethod(urlPath="/all")
    public List<Department> getAllDepartment(){
List<Department> dep = null;
        /*Department dep1 = new Department();
        dep1.setId(34l);
        dep1.setDepartmentName("vioara");
        Department dep2 = new Department();
        dep2.setId(334l);
        dep2.setDepartmentName("vioara");

        Department dep12= new Department();
        dep12.setId(334l);
        dep12.setDepartmentName("vioara");
        dep.add(dep1);
        dep.add(dep2);
        dep.add(dep12);*/
            DepartmentServiceImpl d= new DepartmentServiceImpl();
            dep=d.findAllDepartment();

        return dep ;
    }
        @MyRequestMethod(urlPath = "/one")
        public Department getOneDepartment(String id){
                DepartmentServiceImpl n = new DepartmentServiceImpl();
                Department emp = n.findOneDepartment(Long.parseLong(id));

                return emp;

        }
}
