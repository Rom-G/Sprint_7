package ru.scooter.data;

import com.github.javafaker.Faker;

public class CourierData {

    static Faker user = new Faker();
    public final static String LOGIN = user.name().lastName() + user.regexify("[0-9]{4}");
    public final static String PASSWORD = user.regexify("[0-9]{4}");
    public final static String FIRSTNAME = user.name().firstName();
    public final static int NON_EXISTENT_COURIER = user.number().numberBetween(10000000, 99999999);

    public final static String COURIER_CREATE_PATH = "/api/v1/courier";
    public final static String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    public final static String LOGIN_ALREADY_USE = "Этот логин уже используется";
    public final static String INSUFFICIENT_COURIER_CREATE = "Недостаточно данных для создания учетной записи";
    public final static String INSUFFICIENT_COURIER_DELETE = "Недостаточно данных для удаления курьера";
    public final static String COURIER_NOT_EXIST = "Курьера с таким id нет";
    public final static String INSUFFICIENT_COURIER_LOGIN = "Недостаточно данных для входа";
    public final static String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";
}
