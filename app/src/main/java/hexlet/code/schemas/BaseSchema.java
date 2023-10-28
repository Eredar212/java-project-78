package hexlet.code.schemas;

import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    //минимальную проверку на null необходимо сделать вне очереди
    protected boolean needCheckRequired;
    protected Predicate<Object> validation = this::checkInstance;

    @SuppressWarnings("unchecked")
    public T required() {
        needCheckRequired = true;
        return (T) this;
    }

    //в каждом классе расширения должен быть переопределен метод checkInstance
    protected abstract boolean checkInstance(Object o);

    public final boolean isValid(Object tested) {
        //Если объект null и не указана проверка на обязательность - объект валидный не зависимо от остальных условий.
        //Если объект null и обязательность указана - объект невалидный не зависимо от остальных условий.
        if (tested == null) {
            return !needCheckRequired;
        }
        return validation.test(tested);
    }
}
