package hexlet.code.schemas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseSchema<T> {
    protected Set<String> needCheckMethods = new HashSet<>();
    private boolean needCheckRequired;

    @SuppressWarnings("unchecked")
    public final T required() {
        needCheckMethods.add("checkRequired");
        needCheckRequired = true;
        return (T) this;
    }

    //простая проверка, актуальна не для всех типов
    //например, для "Строка" метод переопределен
    private boolean checkRequired(Object tested) {
        return tested != null;
    }

    //в каждом классе расширения должен быть переопределен метод checkInstance
    protected abstract boolean checkInstance(Object o);

    public final boolean isValid(Object tested) {
        try {
            //Если объект null, то для любого типа он не пройдет проверку на обязательность.
            //Но для некоторых типов (прим. Строка) null - не единственное ошибочное состояние,
            //поэтому вызов метода checkRequired не исключаем.
            //В обратную сторону - если объект null и не указана проверка на обязательность,
            //то такой объект валидный не зависимо от остальных условий.
            if (tested == null) {
                return !needCheckRequired;
            }

            //проверяем валидность класса передаваемого объекта, если он не null
            //если объект не валиден по классу, то смысла в дальнейшей проверки нет
            Method checkInstanceMethod = this.getClass().getDeclaredMethod("checkInstance", Object.class);
            checkInstanceMethod.setAccessible(true);
            if (!(boolean) checkInstanceMethod.invoke(this, tested)) {
                return false;
            }

            //проверяем условия для объекта
            if (!needCheckMethods.isEmpty()) {
                for (String m : needCheckMethods) {
                    Method method = this.getSchemaMethod(m);
                    if (method == null) {
                        throw new RuntimeException("Unexpected method: " + m);
                    }
                    method.setAccessible(true);
                    if (!(boolean) method.invoke(this, tested)) {
                        return false;
                    }

                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //некоторые методы могут быть определены в базовой схеме BaseScheme при расширяемости проекта
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
