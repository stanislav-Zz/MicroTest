package com.example.MicroTest.constants;

public enum Constant {

    USER_ID_CANNOT_BE_NULL("ID пользователя не может быть null"),
    SUBSCRIPTIONS_CANNOT_BE_NULL("Данные подписки не могут быть null"),
    SERVICE_NAME_CANNOT_BE_NULL("Название сервиса не может быть пустым"),
    USER_NOT_FOUND("Пользователь с ID {} не найден"),
    ID_SUBSCRIPTION_NOT_NULL("ID подписки не может быть null"),
    USER_ID_NOT_NULL("ID пользователя не может быть null"),
    USER_ID_NOT_FOUND("Пользователь не найден с id: "),
    SUBSCRIPTION_ID_NOT_NULL("ID подписки не может быть null"),
    SUBSCRIPTION_ID_NOT_FOUND("Подписка не найдена"),
    USER_NOT_NULL("Пользователь не может быть null"),
    USERNAME_NOT_NULL("Имя пользователя не может быть null"),
    LASTNAME_NOT_NULL("Фамилия пользователя не может быть null"),
    CLIENT_DATA_NOT_NULL("Данные пользователя не могут быть null")

    ;


    Constant(String s) {
    }


}
