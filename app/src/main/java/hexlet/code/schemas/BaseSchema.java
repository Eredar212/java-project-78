package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    @SuppressWarnings("rawtypes")
    protected List<Predicate> checkList = new ArrayList<>();

    //минимальную проверку на null необходимо сделать вне очереди
    private boolean needCheckRequired;

    public BaseSchema() {
        checkList.add(this::checkInstance);
    }

    @SuppressWarnings("unchecked")
    public final T required() {
        needCheckRequired = true;
        return (T) this;
    }

    protected abstract boolean isNullOrEmpty(Object o);

    //в каждом классе расширения должен быть переопределен метод checkInstance
    protected abstract boolean checkInstance(Object o);

    @SuppressWarnings("unchecked")
    public final boolean isValid(Object tested) {
        // пустой объект проверяется только на условие required, если оно определено
        if (isNullOrEmpty(tested)) {
            return !needCheckRequired;
        }

        return checkList.stream()
                .allMatch(o -> o.test(tested));
    }
}
