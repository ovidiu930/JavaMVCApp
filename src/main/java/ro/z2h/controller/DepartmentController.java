package ro.z2h.controller;


import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;
import ro.z2h.service.DepartmentService;
import ro.z2h.service.DepartmentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@MyController(urlPath = "/department")
public class DepartmentController {

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
        dep=d.findAllDepartments();

        return dep ;
    }

    @MyRequestMethod(urlPath = "/one")
    public Department getOneDepartments(String id) {
        Department department = new Department();
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
        department = departmentService.findOneDepartment(Long.valueOf(id));
        return department;
    }
}
