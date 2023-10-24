package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private static Validator v;

    @BeforeAll
    static void validatorInit() {
        v = new Validator();
    }

    @Test
    void simpleTestString() {
        StringSchema schema = v.string();
        // Пока не вызван метод required(), null и пустая строка считаются валидным
        assertThat(schema.isValid("")).isTrue(); // true
        assertThat(schema.isValid(null)).isTrue(); // true
        assertThat(schema.isValid(5)).isFalse(); // false
        assertThat(schema.isValid("hexlet")).isTrue(); // true

        schema.required();
        assertThat(schema.isValid(null)).isFalse(); // false
        assertThat(schema.isValid("")).isFalse(); // false
        assertThat(schema.isValid(5)).isFalse(); // false
        assertThat(schema.isValid("hexlet")).isTrue(); // true

        schema.minLength(5);
        assertThat(schema.isValid("hexlet")).isTrue(); // true
        assertThat(schema.isValid("true")).isFalse(); // false

        schema.minLength(null); //сбрасываем требования к длине

        assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse(); // false

        assertThat(schema.isValid("what does the fox say")).isFalse(); // false
        // Здесь уже false, так как добавлена еще одна проверка contains("whatthe")
    }

    @Test
    void simpleTestNumber() {
        NumberSchema schema = v.number();
        // Пока не вызван метод required(), null считается валидным
        assertThat(schema.isValid(null)).isTrue(); // true
        assertThat(schema.isValid("5")).isFalse(); // false
        assertThat(schema.isValid(-10)).isTrue(); // true
        assertThat(schema.positive().isValid(null)).isTrue(); // true

        schema.required();

        assertThat(schema.isValid(null)).isFalse(); // false
        assertThat(schema.isValid("5")).isFalse(); // false
        assertThat(schema.isValid(10)).isTrue(); // true

        // Потому что ранее мы вызвали метод positive()
        assertThat(schema.isValid(-10)).isFalse(); // false
        //  Ноль — не положительное число
        assertThat(schema.isValid(0)).isFalse(); // false

        schema.range(5, 10);
        assertThat(schema.isValid(5)).isTrue(); // true
        assertThat(schema.isValid(10)).isTrue(); // true
        assertThat(schema.isValid(4)).isFalse(); // false
        assertThat(schema.isValid(11)).isFalse(); // false
    }

    @Test
    void simpleTestMap() {
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue(); // true

        schema.required();

        assertThat(schema.isValid(null)).isFalse(); // false
        assertThat(schema.isValid(new HashMap<>())).isTrue(); // true

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue(); // true

        schema.sizeof(2);

        assertThat(schema.isValid(data)).isFalse();  // false
        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue(); // true
    }

    @Test
    void mapSchemasShapeTest() {

        MapSchema schema = v.map();

        // shape позволяет описывать валидацию для значений каждого ключа объекта Map
        // Создаем набор схем для проверки каждого ключа проверяемого объекта
        // Для значения каждого ключа - своя схема
        Map<String, BaseSchema> schemas = new HashMap<>();

        // Определяем схемы валидации для значений свойств "name" и "age"
        // Имя должно быть строкой, обязательно для заполнения
        schemas.put("name", v.string().required());
        // Возраст должен быть положительным числом
        schemas.put("age", v.number().positive());

        // Настраиваем схему `MapSchema`
        // Передаем созданный набор схем в метод shape()
        schema.shape(schemas);

        // Проверяем объекты
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertThat(schema.isValid(human1)).isTrue(); // true

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema.isValid(human2)).isTrue(); // true

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(schema.isValid(human3)).isFalse(); // false

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertThat(schema.isValid(human4)).isFalse(); //false

        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Petya");
        assertThat(schema.isValid(human5)).isFalse(); //false

        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Masha");
        human6.put("ageS", 10);
        human6.put("title", "tester");
        assertThat(schema.isValid(human6)).isFalse(); //false

        Map<String, Object> human7 = new HashMap<>();
        human7.put("name", "Petya");
        human7.put("age", 15);
        human7.put("title", "tester2");
        assertThat(schema.isValid(human7)).isTrue(); //true
    }
}
