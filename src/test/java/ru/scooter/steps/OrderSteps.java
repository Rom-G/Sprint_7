package ru.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.scooter.client.ApiClient;
import ru.scooter.models.OrderModel;
import ru.scooter.models.OrdersListModel;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static ru.scooter.data.OrderData.*;

public class OrderSteps {

    @Step("Send POST request to /api/v1/orders")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH);
    }

    @Step("Send PUT request to /api/v1/orders/cancel")
    public static Response cancelOrder(int track) {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(Collections.singletonMap("track", track))
                .when()
                .put(ORDER_CANCEL_PATH);
    }

    @Step("Send GET request to /api/v1/orders")
    public static OrdersListModel getAllOrders() {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .when()
                .get(ORDER_CREATE_PATH)
                .as(OrdersListModel.class);
    }
}
