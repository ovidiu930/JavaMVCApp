package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Job;
import ro.z2h.service.JobServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovy on 11/13/2014.
 */
@MyController(urlPath="/job")
public class JobController {
    @MyRequestMethod(urlPath="/all")
    public List<Job> getAllJob(){
        List<Job> n = new ArrayList<Job>();
        JobServiceImpl jobs= new JobServiceImpl();
        n=jobs.findAllJobs();
        return n;
    }


    @MyRequestMethod(urlPath = "/one")
    public Job getOneJob(String id) {
        Job job = new Job();
        JobServiceImpl oneJob = new JobServiceImpl();
        job = oneJob.findOneJob(id);
        return job;
    }
}