package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService{

    public List<Department> findAllDepartments() {
        List<Department> allDepartment = new ArrayList<Department>();
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        DepartmentDao departmentDaoDao = new DepartmentDao();

        try {
            allDepartment = departmentDaoDao.getAllDepartments(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDepartment;
    }

    public Department findOneDepartment(Long id){
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        Department department = new Department();
        DepartmentDao departmentDao = new DepartmentDao();
        try {
            department = departmentDao.getDepartmentById(con, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }
}
