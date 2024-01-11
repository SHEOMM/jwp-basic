package core.ref;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        assertEquals(clazz.getName(), "next.model.Question");
        logger.debug("constructor" +Arrays.toString(clazz.getDeclaredConstructors()));
        logger.debug("fields" + Arrays.toString(clazz.getDeclaredFields()));
        logger.debug("method "+ Arrays.toString(clazz.getDeclaredMethods()));
    }
    
    @Test
    public void newInstanceWithConstructorArgs()
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> declaredConstructor1 = declaredConstructors[0];
        User user = (User)declaredConstructor1.newInstance("userId", "password", "name", "email");
        assertEquals(user.getUserId(), "userId");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getName(), "name");
        assertEquals(user.getEmail(), "email");

    }
    
    @Test
    public void privateFieldAccess() throws IllegalAccessException {
        Class<Student> clazz = Student.class;
        Student student = new Student();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if(declaredField.getName().equals("name")){
                declaredField.set(student, "hi");
            }else{
                declaredField.set(student, 12);
            }

        }
        assertEquals(student.getName(), "hi");
        assertEquals(student.getAge(), 12);
    }
}
