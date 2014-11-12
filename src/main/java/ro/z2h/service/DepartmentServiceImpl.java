package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.util.List;

/**
 * Created by ovy on 11/12/2014.
 */
public class DepartmentServiceImpl implements DepartmentService {
    public List<Department> findAllDepartment() {
        DatabaseManager n = new DatabaseManager();
        Connection con = n.getConnection("ZTH_04", "passw0rd");
        DepartmentDao emp = new DepartmentDao();
        List<Department> ang = null;
        try {
            ang = emp.getAllDepartments(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return ang;
    }

    public Department findOneDepartment(Long id) {


        DatabaseManager n = new DatabaseManager();
        Connection con = n.getConnection("ZTH_04", "passw0rd");
        DepartmentDao emp = new DepartmentDao();
        Department ang = null;
        try {
            ang = emp.getDepartmentById(con, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return ang;
    }
}

