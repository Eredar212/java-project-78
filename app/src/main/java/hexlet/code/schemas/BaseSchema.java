package hexlet.code.schemas;

import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    //минимальную проверку на null необходимо сделать вне очереди
    private boolean needCheckRequired;
    protected Predicate<Object> validation = this::checkInstance;

    @SuppressWarnings("unchecked")
    public final T required() {
        needCheckRequired = true;
        return (T) this;
    }

    //проверка пустого НЕ null объекта, примеры Строка - "", карта - new HashMap<>()
    protected abstract boolean isEmpty(Object o);

    //в каждом классе расширения должен быть переопределен метод checkInstance
    protected abstract boolean checkInstance(Object o);

    public final boolean isValid(Object tested) {
        //Если объект null и не указана проверка на обязательность - объект валидный не зависимо от остальных условий.
        //Если объект null и обязательность указана - объект невалидный не зависимо от остальных условий.

        //Вынужденная выноска на случай если схеме проверку required() ставят позже других,
        //пример без выноски
        //new Validator().string().minLength(5).required().isValid(null);
        //                                                      => Cannot invoke "String.length()" because "o" is null
        if (tested == null || isEmpty(tested)) {
            return !needCheckRequired;
        }
        return validation.test(tested);
    }
}
