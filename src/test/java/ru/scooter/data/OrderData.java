package ru.scooter.data;

import com.github.javafaker.Faker;
import ru.scooter.models.OrderModel;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class OrderData {

    static Faker order = new Faker();
    public final static String FIRSTNAME = order.name().firstName();
    public final static String LASTNAME = order.name().lastName();
    public final static String ADDRESS = order.address().streetAddress();
    public final static String METRO = order.regexify("[0-9]{2}");
    public final static String PHONE = "+79" + order.regexify("[0-9]{9}");
    public final static int RENT_TIME = order.number().numberBetween(1, 7);
    public final static String DELIVERY_DATE = order.date()
            .future(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    public final static String COMMENT = order.chuckNorris().fact();
    public final static int NON_EXISTENT_ORDER = order.number().numberBetween(1000000, 9999999);
    public final static String ORDER_CREATE_PATH = "/api/v1/orders";
    public final static String ORDER_ACCEPT_PATH = "/api/v1/orders/accept";
    public final static String ORDER_CANCEL_PATH = "/api/v1/orders/cancel";
    public final static String ORDER_GET_PATH = "/api/v1/orders/track";

    public static OrderModel validOrder(Colors colors) {
        return OrderModel.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .address(ADDRESS)
                .metroStation(METRO)
                .phone(PHONE)
                .rentTime(RENT_TIME)
                .deliveryDate(DELIVERY_DATE)
                .comment(COMMENT)
                .color(preferColor(colors))
                .build();
    }

    private static String[] preferColor(Colors colors) {
        if (colors == Colors.BLACK) {
            return new String[]{"BLACK"};
        } else if (colors == Colors.GREY) {
            return new String[]{"GREY"};
        } else if (colors == Colors.BLACK_AND_GREY) {
            return new String[]{"BLACK", "GREY"};
        } else {
            return new String[]{};
        }
    }

    public enum Colors {
        BLACK,
        GREY,
        BLACK_AND_GREY,
        NO_COLOR
    }
}
