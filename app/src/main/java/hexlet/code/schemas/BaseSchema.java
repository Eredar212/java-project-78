package hexlet.code.schemas;

import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    //минимальную проверку на null необходимо сделать вне очереди
    private boolean needCheckRequired;
    protected Predicate<Object> validation = this::checkInstance;

    @SuppressWarnings("unchecked")
    public final T required() {
        needCheckRequired = true;
        validation = validation.and(o -> {
            //для "Строка" проверяются два условия при required
            if (this instanceof StringSchema) {
                return o != null && !o.toString().isEmpty();
            }
            return o != null;
        });
        return (T) this;
    }

    //в каждом классе расширения должен быть переопределен метод checkInstance
    protected abstract boolean checkInstance(Object o);

    public final boolean isValid(Object tested) {
        //Если объект null и не указана проверка на обязательность - объект валидный не зависимо от остальных условий.
        //Если объект null и обязательность указана - объект невалидный не зависимо от остальных условий.

        //Вынужденная выноска на случай если схеме проверку required() ставят позже других,
        //пример без выноски
        //new Validator().string().minLength(5).required().isValid(null);
        //                                                      => Cannot invoke "String.length()" because "o" is null
        if (tested == null) {
            return !needCheckRequired;
        }
        return validation.test(tested);
    }
}
