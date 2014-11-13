package ro.z2h.service;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class    EmployeeServiceImpl implements EmployeeService {

    public List<Employee> findAllEmployees() {
        List<Employee> allEmployees = new ArrayList<Employee>();
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        EmployeeDao emplDao = new EmployeeDao();

        try {
            allEmployees = emplDao.getAllEmployees(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployees;
    }

    public Employee findOneEmployee(Long id){
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        Employee employee = new Employee();
        EmployeeDao emplDao = new EmployeeDao();
        try {
            employee = emplDao.getEmployeeById(con, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public void deleteOneEmployee(Long id) {
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = new Employee();
        try {
            employee = employeeDao.getEmployeeById(con, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employeeDao.deleteEmployee(employee, con);
    }
}
