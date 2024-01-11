package core.reflections;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

@Slf4j
public class ControllerScanner {
    private Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers(){
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers){
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try{
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.newInstance());
            }
        }catch (InstantiationException | IllegalAccessException e){
            log.error(e.getMessage());
        }
        return controllers;
    }
}
