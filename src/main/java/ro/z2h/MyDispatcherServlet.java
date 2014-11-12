package ro.z2h;

import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentsController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by ovy on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet {
    HashMap<String,MethodAttributes> has = new HashMap<String,MethodAttributes>();

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

                if (clase.isAnnotationPresent(MyController.class)) {

                    System.out.println(((MyController) clase.getAnnotation(MyController.class)).urlPath());
                    // System.out.println(((MyRequestMethod)clase.getAnnotation(MyRequestMethod.class)).methodType());


                    Method[] m = clase.getDeclaredMethods();
                    //iter+tab
                    for (Method m1 : m) {
                        if (m1.isAnnotationPresent(MyRequestMethod.class)) {
                            System.out.println(((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).methodType());
                            System.out.println(((MyController) clase.getAnnotation(MyController.class)).urlPath()+((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).urlPath());
                            String key=((MyController) clase.getAnnotation(MyController.class)).urlPath()+((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).urlPath();
                           // String value=clase.getName()+m1.getName()+((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).methodType();
                            MethodAttributes e = new MethodAttributes();
                            e.setControllerClass(clase.getName());
                            e.setMethodName(m1.getName());
                            e.setMethodType(((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).methodType());
                            has.put(key,e);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(has);

            }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReplay("POST", req, resp);
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
        ObjectMapper mapper = new ObjectMapper();
       String s= mapper.writeValueAsString(r);
        writer.printf(s);
    }

    /*Where an application conttroler should be return*/
   ;
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        //MyRequestMethod runner = new MyRequestMethod();
        //Method[] methods = runner.getClass().getMethods();
        /*test return Hello*/
        /*for test/employee i want all employees for applicationControler*/
      //  Class newClass =  new Class();

       MethodAttributes val= has.get(req.getPathInfo());
        if(val!= null) {
            try {
                Class controllerClass = Class.forName(val.getControllerClass());
                Object controllerInstance = controllerClass.newInstance();

               String a= req.getParameter("id");
                System.out.println(a);
               if(a!=null) {

                   Method method = controllerClass.getMethod(val.getMethodName(), String.class);
                    Object responseProcesare = method.invoke(controllerInstance,a);
                    return responseProcesare;
               }
                else{
                   Method method = controllerClass.getMethod(val.getMethodName());
                   Object responseProcesare = method.invoke(controllerInstance);
                   return responseProcesare;
               }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       /* r.split("/");
        if (has.containsKey(r.)) {
            try{
                Iterable<Class> clases = AnnotationScanUtils.getClasses("ro.z2h.controller");
                for (Class clase : clases){
                    if(((MyController)clase.getAnnotation(MyController.class)).urlPath().equals(r)){
                        Method[] m=clase.getDeclaredMethods();
                        for(Method m1:m) {

                          if(((MyRequestMethod) m1.getAnnotation(MyRequestMethod.class)).urlPath().equals());


                }
            }catch(Exception e){
                e.printStackTrace();
            }*/

      /* if (r.startsWith("/employee")) {
            EmployeeController e = new EmployeeController();
            return e.getAllEmployees();
        } else if (r.startsWith("/department")) {
                DepartmentsController e = new DepartmentsController();
                return e.getAllDepartment();
            } else {

                return "Hello!";
            }*/
            return null;

        }

    }



