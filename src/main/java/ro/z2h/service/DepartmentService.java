package ro.z2h.service;

import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;

import java.util.List;

/**
 * Created by ovy on 11/12/2014.
 */
public interface DepartmentService {
    List<Department> findAllDepartment();
    Department findOneDepartment(Long id);
}
