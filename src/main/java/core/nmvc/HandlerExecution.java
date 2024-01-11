package core.nmvc;

import core.mvc.ModelAndView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerExecution {
    private Object declaredObject;
    private Method method;

    public HandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            return (ModelAndView) method.invoke(declaredObject, request, response);
        }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }

    }
}
