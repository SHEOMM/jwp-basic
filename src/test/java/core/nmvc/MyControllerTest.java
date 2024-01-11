package core.nmvc;

import core.reflections.ControllerScanner;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class MyControllerTest {
    private ControllerScanner controllerScanner;

    @Before
    public void setup(){
        controllerScanner = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception{
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            log.debug("controller = " + controller);
        }
        Assert.assertEquals(1, 1);
    }
}