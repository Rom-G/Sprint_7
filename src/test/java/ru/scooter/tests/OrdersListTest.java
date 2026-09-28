package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.scooter.models.OrdersListModel;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertTrue;
import static ru.scooter.steps.OrderSteps.*;

public class OrdersListTest {

    @Test
    @DisplayName("Getting a list of orders")
    @Description("The test verifies that a list of orders in response is received orders")
    public void getOrdersListNotEmptyTest() {
        Response getAllOrdersResp = getAllOrders();

        OrdersListModel ordersListModel = getAllOrdersResp
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(OrdersListModel.class);

        int size = ordersListModel.getOrders().size();
        assertTrue("List must be not empty, but size = " + size, size > 0);
    }
}
