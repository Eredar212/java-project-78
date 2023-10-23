package hexlet.code;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BaseSchema {
    protected List<String> needCheckMethods = new ArrayList<>();

    public BaseSchema required() {
        needCheckMethods.add("checkRequired");
        return this;
    }

    private boolean checkRequired(Object tested) {
        return tested != null;
    }

    public boolean isValid(Object tested) {
        //проверяем валидность класса передаваемого объекта, если он не null
        //если объект не валиден по классу, то смысла в дальнейшей проверки нет
        try {
            Method checkInstanceMethod = this.getClass().getDeclaredMethod("checkInstance", Object.class);
            checkInstanceMethod.setAccessible(true);
            if (!(boolean) checkInstanceMethod.invoke(this, tested)) {
                return false;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        //проверяем условия для объекта
        if (!needCheckMethods.isEmpty()) {
            for (String m : needCheckMethods) {
                Method method = this.getSchemaMethod(m);
                if (m == null) {
                    throw new RuntimeException("Unexpected method: " + m);
                }
                try {
                    method.setAccessible(true);
                    if (!(boolean) method.invoke(this, tested)) {
                        return false;
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    //некоторые методы могут быть определены в базовой схеме BaseScheme или выше при расширяемости проекта
    //поэтому ищем метод от текущего класса и вверх
    private Method getSchemaMethod(String methodName) {
        Method method = null;
        for (Class<?> c = this.getClass(); c != Object.class; c = c.getSuperclass()) {
            try {
                method = c.getDeclaredMethod(methodName, Object.class);
                return method;
            } catch (NoSuchMethodException e) {
                //ничего не делаем, ищем дальше
            }
        }
        return method;
    }
}
