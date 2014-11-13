package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.dao.JobDao;
import ro.z2h.domain.Employee;
import ro.z2h.domain.Job;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.util.List;

/**
 * Created by ovy on 11/13/2014.
 */
public class JobServiceImpl {
    public List<Job> findAllJobs() {
        DatabaseManager n = new DatabaseManager();
        Connection con = n.getConnection("ZTH_04","passw0rd");
        JobDao emp = new JobDao();
        List<Job> ang=null;
        try {
            ang = emp.getAllJobs(con);
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


    public Job findOneJob(String id) {
        Connection con = DatabaseManager.getConnection("ZTH_04", "passw0rd");
        Job job = new Job();
        JobDao jobDao = new JobDao();
        try {
            job = jobDao.getJobById(con, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job;
    }
}