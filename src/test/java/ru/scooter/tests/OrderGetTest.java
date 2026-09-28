package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.scooter.models.OrderGetModel;
import ru.scooter.models.OrderModel;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static ru.scooter.data.OrderData.*;
import static ru.scooter.data.OrderData.Colors.NO_COLOR;
import static ru.scooter.steps.OrderSteps.*;

public class OrderGetTest {

    @Test
    @DisplayName("Check the order can be received")
    @Description("A successful GET request for /api/v1/orders/track returns an object with an order")
    public void getOrderSuccessTest() {
        OrderModel order = validOrder(NO_COLOR);
        Response createOrderResp = createOrder(order);
        int track = createOrderResp.jsonPath().getInt("track");

        Response orderGetResp = getOrderResp(track);
        OrderGetModel orderGetModel = orderGetResp
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(OrderGetModel.class);

        assertNotNull("Order field in the response must not be null", orderGetModel.getOrder());
    }

    @Test
    @DisplayName("Check error for request without track")
    @Description("Request without track for /api/v1/orders/track end point returns 400")
    public void getOrderWithoutTrackFailsTest() {
        getOrderResp(null)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo(ORDER_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Check error for request with non-existent track")
    @Description("Request with non-existent track for /api/v1/orders/track end point returns 404")
    public void getOrderWithFakeTrackFailsTest() {
        getOrderResp(NON_EXISTENT_ORDER)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo(ORDER_NOT_FOUND));
    }
}
