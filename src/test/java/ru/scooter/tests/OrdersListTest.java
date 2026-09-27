package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.scooter.models.Order;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.scooter.steps.OrderSteps.*;

public class OrdersListTest {

    @Test
    @DisplayName("Getting a list of orders")
    @Description("The test verifies that a list of orders is received in response")
    public void checkResponseContainsOrders() {
        List<Order> ordersList = getAllOrders().getOrders();

        assertThat("Orders list is empty", ordersList.size(), greaterThan(0));
    }
}
