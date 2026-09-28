package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.scooter.models.OrderGet;
import ru.scooter.models.OrderGetModel;
import ru.scooter.models.OrderModel;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static ru.scooter.data.OrderData.*;
import static ru.scooter.data.OrderData.Colors.NO_COLOR;
import static ru.scooter.steps.OrderSteps.*;

public class OrderGetTest {

    @Test
    @DisplayName("Check the order can be received")
    @Description("A successful GET request for /api/v1/orders/track returns an object with an order")
    public void getOrderSuccess() {
        OrderModel order = validOrder(NO_COLOR);
        Response createOrderResp = createOrder(order);
        int track = createOrderResp.jsonPath().getInt("track");

        Response orderGetResp = getOrderResp(track);
        orderGetResp
                .then()
                .log().all()
                .statusCode(200);

        if (orderGetResp.statusCode() == 200) {
            OrderGet orderGet = orderGetResp
                    .as(OrderGetModel.class)
                    .getOrder();

            assertNotNull("Order in response is null", orderGet);

            cancelOrder(track);
        }
    }

    @Test
    @DisplayName("Check error for request without track")
    @Description("Request without track for /api/v1/orders/track end point returns 400")
    public void getOrderWithoutTrackFails() {
        getOrderResp(null)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(ORDER_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Check error for request with non-existent track")
    @Description("Request with non-existent track for /api/v1/orders/track end point returns 404")
    public void getOrderWithFakeTrackFails() {
        getOrderResp(NON_EXISTENT_ORDER)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(ORDER_NOT_FOUND));
    }
}
