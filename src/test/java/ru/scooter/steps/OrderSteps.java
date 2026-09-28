package ru.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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

    @Step("Send PUT request to /api/v1/orders/accept/:id")
    public static Response acceptOrder(Integer courierId, Integer orderId) {
        RequestSpecification spec = given()
                .log().all()
                .spec(ApiClient.requestSpec());

        if (courierId != null) {
            spec.queryParam("courierId", courierId.toString());
        }

        String path = orderId != null ? ORDER_ACCEPT_PATH + "/" + orderId : ORDER_ACCEPT_PATH;

        return spec
                .when()
                .put(path);
    }

    @Step("Send PUT request to /api/v1/orders/cancel")
    public static void cancelOrder(int track) {
        given()
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
                .get(ORDER_CREATE_PATH)
                .as(OrdersListModel.class);
    }

    @Step("Send GET request to /api/v1/orders/track")
    public static Response getOrderResp(Integer track) {
        RequestSpecification spec = given()
                .log().all()
                .spec(ApiClient.requestSpec());

        if (track != null) {
            spec.queryParam("t", track);
        }

        return spec
                .get(ORDER_GET_PATH);
    }
}
