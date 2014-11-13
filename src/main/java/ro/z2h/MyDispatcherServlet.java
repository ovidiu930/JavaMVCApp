package ro.z2h;

import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

public class MyDispatcherServlet extends HttpServlet {

    HashMap<String, MethodAttributes> myMap = new HashMap<String, MethodAttributes>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("POST", req, resp);
    }

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        req.getMethod();
        /* Dispatch */
        Object r = dispatch(httpMethod, req, resp);

        /*Reply*/
        reply(r, req, resp);

        /* Tratarea erorilor */
        Exception ex = null;
        sendException(ex, req, resp);
    }

    /*Where an application controller should be  called.*/
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        /*pentru / test = Hello */
        /*pentru / employee => allEmployees de la Application Controller*/
        String pathInfo = req.getPathInfo();
        MethodAttributes methodAttributes = myMap.get(pathInfo);
        try {
            if (methodAttributes != null) {

                Class<?> appControllerClass = Class.forName(methodAttributes.getControllerClass());
                Object appControllerInstance = appControllerClass.newInstance();
                Method controllerMethod = appControllerClass.getMethod(methodAttributes.getMethodName(), methodAttributes.getMethodParametersType());

                Parameter[] parameters = controllerMethod.getParameters();
                ArrayList<String> parametrii = new ArrayList<>();
                for (Parameter realMethodParameter : parameters) {
                    String parameter = req.getParameter(realMethodParameter.getName());
                    parametrii.add(parameter);
                }

                return controllerMethod.invoke(appControllerInstance,  (String[]) parametrii.toArray(new String[0]));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "Hello";
    }

    /*Used to send the view to the client*/
    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(r);
        PrintWriter writer = resp.getWriter();
        writer.printf(valueAsString);
    }

    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("There was an exception!");
    }

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");

            for (Class aClass : classes) {
                if (aClass.isAnnotationPresent(MyController.class)) {
                    String urlClass = ((MyController) aClass.getAnnotation(MyController.class)).urlPath();
                    System.out.println(urlClass);
                    for (Method method : aClass.getMethods()) {
                        if (method.isAnnotationPresent(MyRequestMethod.class)) {
                            String urlMethod = ((MyRequestMethod) method.getAnnotation(MyRequestMethod.class)).urlPath();
                            System.out.println(urlClass);
                            System.out.println(urlMethod);
                            String urlKey = urlClass + urlMethod;
                            MethodAttributes ma = new MethodAttributes();
                            ma.setControllerClass(aClass.getName());
                            ma.setMethodType(((MyRequestMethod) method.getAnnotation(MyRequestMethod.class)).methodType());
                            ma.setMethodName(method.getName());
                            ma.setMethodParametersType(method.getParameterTypes());
                            myMap.put(urlKey, ma);
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}