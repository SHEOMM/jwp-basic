package core.nmvc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.nmvc.HandlerExecution;
import core.reflections.ControllerScanner;
import core.reflections.HandlerKey;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

@Slf4j
public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<core.reflections.HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();


    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize(){
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        Set<Method> methods = getRequestMappingMethods(
                controllers.keySet()
        );
        for (Method method : methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(
                    createHandlerKey(rm),
                    new HandlerExecution(controllers.get(method.getDeclaringClass()), method)
            );
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(
                request.getMethod().toUpperCase()
        );
        return handlerExecutions.get(new core.reflections.HandlerKey(requestURI, rm));
    }

    private core.reflections.HandlerKey createHandlerKey(RequestMapping rm){
        return new HandlerKey(rm.value(), rm.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers){

        HashSet<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : controllers) {
            requestMappingMethods.addAll(
                    ReflectionUtils.getAllMethods(
                            clazz, ReflectionUtils.withAnnotation(RequestMapping.class)
                    )
            );
        }
        return requestMappingMethods;
    }
}
