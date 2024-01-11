package core.ref;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if(declaredMethod.isAnnotationPresent(MyTest.class)){
                assertEquals(declaredMethod.getName().length(), 3);
            }
        }
    }
}
