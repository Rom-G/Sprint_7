package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.scooter.models.CourierModel;
import ru.scooter.models.OrderGetModel;
import ru.scooter.models.OrderModel;

import static org.hamcrest.Matchers.equalTo;
import static ru.scooter.data.CourierData.*;
import static ru.scooter.data.OrderData.*;
import static ru.scooter.data.OrderData.Colors.*;
import static ru.scooter.steps.CourierSteps.*;
import static ru.scooter.steps.OrderSteps.*;

public class OrderAcceptTest {

    @Test
    @DisplayName("Check the order can be accepted")
    @Description("Basic test for /api/v1/orders/accept/:id endpoint")
    public void acceptOrderSuccessTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
        Response loginResp = loginCourier(courier);
        int courierId = loginResp.jsonPath().getInt("id");

        OrderModel order = validOrder(NO_COLOR);
        Response createOrderResp = createOrder(order);
        int track = createOrderResp.jsonPath().getInt("track");

        OrderGetModel orderResp = getOrderResp(track).as(OrderGetModel.class);
        int orderId = orderResp.getOrder().getId();

        acceptOrder(courierId, orderId)
                .then()
                .log().all()
                .statusCode(200)
                .body("ok", equalTo(true));

        cancelOrder(track);
    }

    @Test
    @DisplayName("Check the order can't be accepted without courier id")
    @Description("Test verifies for /api/v1/orders/accept/:id endpoint if the courier id is not transmitted")
    public void acceptOrderWithoutCourierIdFailsTest() {
        OrderModel order = validOrder(NO_COLOR);
        Response createOrderResp = createOrder(order);
        int track = createOrderResp.jsonPath().getInt("track");

        OrderGetModel orderResp = getOrderResp(track).as(OrderGetModel.class);
        int orderId = orderResp.getOrder().getId();

        acceptOrder(null, orderId)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(ORDER_INSUFFICIENT_DATA));

        cancelOrder(track);
    }

    @Test
    @DisplayName("Check the order can't be accepted with non-existent courier id")
    @Description("Test verifies that /api/v1/orders/accept/:id endpoint is working correctly")
    public void acceptOrderWithFakeCourierIdFailsTest() {
        OrderModel order = validOrder(NO_COLOR);
        Response createOrderResp = createOrder(order);
        int track = createOrderResp.jsonPath().getInt("track");

        OrderGetModel orderResp = getOrderResp(track).as(OrderGetModel.class);
        int orderId = orderResp.getOrder().getId();

        acceptOrder(NON_EXISTENT_COURIER, orderId)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(ORDER_COURIER_NOT_EXIST));

        cancelOrder(track);
    }

    @Test
    @DisplayName("Check the order can't be accepted without order id")
    @Description("Test verifies for /api/v1/orders/accept/:id endpoint if the order id is not transmitted")
    public void acceptOrderWithoutOrderIdFailsTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
        Response loginResp = loginCourier(courier);
        int courierId = loginResp.jsonPath().getInt("id");

        acceptOrder(courierId, null)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(ORDER_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Check the order can't be accepted with non-existent order id")
    @Description("Test verifies for /api/v1/orders/accept/:id endpoint if the order id is not transmitted")
    public void acceptOrderWithFakeOrderIdFailsTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
        Response loginResp = loginCourier(courier);
        int courierId = loginResp.jsonPath().getInt("id");

        acceptOrder(courierId, NON_EXISTENT_ORDER)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(ORDER_NOT_EXIST));
    }

    @After
    public void tearDown() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        Response loginResp = loginCourier(courier);

        if (loginResp.statusCode() == 200) {
            int courierId = loginResp.jsonPath().getInt("id");
            deleteCourier(courierId);
        }
    }
}
