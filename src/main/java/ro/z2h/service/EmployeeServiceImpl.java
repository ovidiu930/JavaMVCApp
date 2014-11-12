package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovy on 11/12/2014.
 */
public class EmployeeServiceImpl implements EmployeeService {
    public List<Employee> findAllEmployees() {
        DatabaseManager n = new DatabaseManager();
        Connection con = n.getConnection("ZTH_04","passw0rd");
        EmployeeDao emp = new EmployeeDao();
        List<Employee> ang=null;
        try {
           ang = emp.getAllEmployees(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }


        }
        return ang;
    }
    public Employee findOneEmployee(Long id)
    {
        DatabaseManager n = new DatabaseManager();
        Connection con = n.getConnection("ZTH_04","passw0rd");
        EmployeeDao emp = new EmployeeDao();
       Employee ang=null;
        try {
            ang = emp.getEmployeeById(con,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }


        }
        return ang;
    }




}
