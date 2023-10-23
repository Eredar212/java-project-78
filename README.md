#### Hexlet tests and linter status:
[![Actions Status](https://github.com/Eredar212/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Eredar212/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/7c91576ed20856ace3d3/maintainability)](https://codeclimate.com/github/Eredar212/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/7c91576ed20856ace3d3/test_coverage)](https://codeclimate.com/github/Eredar212/java-project-78/test_coverage)

## Валидатор данных 
Библиотека, с помощью которой можно проверять корректность любых данных.

### Ограничения
Типы данных для проверок:
* Строка
* Число
* Карта данных (ключ-данные)

Проверяемые условия и примеры их применения указаны ниже

### Использование
Для запуска из проекта
```sh
make -C app validate
```

#### Типы данных
Сначала импортируем необходимые классы и создаем объект валидатора
```sh
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
var v = new Validator();
```
Определяем тип данных для проверки
* Строка 
  ```sh
  var stringSchema = v.string();
  ```
* Число
  ```sh
  var numberSchema = v.number();
  ```
* Ключ-данные (карта данных)
  ```sh
  var mapSchema = v.map();
  ```
#### Используемые условия (добавляем проверки)
* Строка
  *  Обязательность
    ```sh
    stringSchema.required();
    ```
  *  Минимальная длина
    ```sh
    stringSchema.minLength();
    ```
  *  Содержит текст
    ```sh
    stringSchema.contains("текст");
    ```
* Число
  *  Обязательность
    ```sh
    numberSchema.required();
    ```
  *  Положительное число
    ```sh
    numberSchema.positive();
    ```
  *  Находится в интервале min max
    ```sh
    numberSchema.range(min, max);
    ```
* Карта данных
  *  Обязательность
    ```sh
    mapSchema.required();
    ```
  *  Размер равен length
    ```sh
    mapSchema.sizeof(length);
    ```
  *  Проверка внутренних данных карты
      * Определяем шаблон данных
        ```sh
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("key1", v.string().required());
        schemas.put("key2", v.number().positive());
        ```
      * Применяем шаблон к нашей карте
        ```sh
        mapSchema.shape(schemas);
        ```
#### Выполняем проверку объекта Object
```sh
stringSchema.isValid(Object);

numberSchema.isValid(Object);

mapSchema.isValid(Object);
```

### Пример работы
[![asciicast](https://asciinema.org/a/616569.svg)](https://asciinema.org/a/616569)
