package core.ref;

import java.lang.reflect.Method;
import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String methodName = declaredMethod.getName();
            if(methodName.startsWith("test")){
                declaredMethod.invoke(clazz.newInstance());
            }
        }
    }
}
