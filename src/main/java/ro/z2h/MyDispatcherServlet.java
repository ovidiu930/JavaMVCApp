package ro.z2h;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentsController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by ovy on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       dispatchReplay("GET",req, resp);
    }

    @Override
    public void init() throws ServletException {

        //super.init();
        try {
            Iterable<Class> clases = AnnotationScanUtils.getClasses("ro.z2h.controller");
            for (Class clase : clases) {

                if(clase.isAnnotationPresent(MyController.class)){

                    System.out.println(((MyController)clase.getAnnotation(MyController.class)).urlPath());

                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

            }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReplay("POST",req, resp);
    }

    private void dispatchReplay(String HttpMethod,HttpServletRequest req, HttpServletResponse resp) {

        /*Dispatch*/
       Object r = dispatch(HttpMethod,req,resp);
        /*Replay*/
        try {
            replay(r, req, resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        /*Catch errors*/
        sendErrors();
        Exception ex=null;
        sendException(ex, req, resp);
    }

    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("There was an exception");
    }

    private void sendErrors() {
    }

    /*Used to replay the answer to the client*/
    private void replay(Object r, HttpServletRequest req, HttpServletResponse resp) throws Exception{
        PrintWriter writer=resp.getWriter();
        writer.printf(r.toString());
    }

    /*Where an application conttroler should be return*/
   ;
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        //MyRequestMethod runner = new MyRequestMethod();
        //Method[] methods = runner.getClass().getMethods();
        /*test return Hello*/
        /*for test/employee i want all employees for applicationControler*/
        String r = req.getPathInfo();
        if (r.startsWith("/employee")) {
            EmployeeController e = new EmployeeController();
            return e.getAllEmployees();
        } else if (r.startsWith("/department")) {
                DepartmentsController e = new DepartmentsController();
                return e.getAllDepartment();
            } else {

                return "Hello!";
            }
        }


}
