package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.scooter.models.CourierModel;

import static org.hamcrest.Matchers.*;
import static ru.scooter.data.CourierData.*;
import static ru.scooter.steps.CourierSteps.*;

public class CourierDeleteTest {

    @Test
    @DisplayName("Check a courier can be delete")
    @Description("Basic test for /api/v1/courier/:id endpoint")
    public void deleteCourierSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
        Response loginResp = loginCourier(courier);
        int courierId = loginResp.jsonPath().getInt("id");

        deleteCourier(courierId)
                .then()
                .log().all()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check for a request without an id")
    @Description("Checks the returned error code (expected 400)")
    public void deleteCourierWithoutIdFails() {
        deleteCourier()
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(INSUFFICIENT_DELETE_COURIER));
    }

    @Test
    @DisplayName("Checking for a request with a non-existent id")
    @Description("Checks the returned error code (expected 404)")
    public void deleteCourierWithFakeIdFails() {
        deleteCourier(NON_EXISTENT_COURIER)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(COURIER_NOT_EXIST));
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
