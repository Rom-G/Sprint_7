package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.scooter.models.OrderModel;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static ru.scooter.data.OrderData.*;
import static ru.scooter.steps.OrderSteps.*;

@RunWith(Parameterized.class)
public class OrderCreateDifferentColorsTest {

    private final OrderModel order;

    public OrderCreateDifferentColorsTest(OrderModel order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][] {
                {validOrder(Colors.BLACK)},
                {validOrder(Colors.GREY)},
                {validOrder(Colors.BLACK_AND_GREY)},
                {validOrder(Colors.NO_COLOR)},
        };
    }

    @Test
    @DisplayName("Create order with different colors")
    @Description("The test checks whether it is possible to select a color when creating an order.")
    public void createOrderWithDifferentColorsSuccessTest() {
        Response orderResp = createOrder(order);
        int track = orderResp.jsonPath().getInt("track");

        orderResp
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        cancelOrder(track);
    }
}
