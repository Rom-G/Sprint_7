package ru.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.scooter.models.CourierModel;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static ru.scooter.data.CourierData.*;
import static ru.scooter.steps.CourierSteps.*;

public class CourierCreateTest {

    @Test
    @DisplayName("Check a courier can be created")
    @Description("Basic test for /api/v1/courier endpoint")
    public void createCourierSuccessTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check impossible to create a duplicate courier")
    @Description("An important test for conflict prevention")
    public void createCourierDuplicateFailsTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo(LOGIN_ALREADY_USE));
    }

    @Test
    @DisplayName("Check impossible to create a courier without login")
    @Description("The test protects against incorrect data in the database")
    public void createCourierWithoutLoginFailsTest() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRSTNAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo(INSUFFICIENT_COURIER_CREATE));
    }

    @Test
    @DisplayName("Check impossible to create a courier without password")
    @Description("The test protects against incorrect data in the database")
    public void createCourierWithoutPasswordFailsTest() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRSTNAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo(INSUFFICIENT_COURIER_CREATE));
    }

    @After
    public void tearDown() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        Response loginResp = loginCourier(courier);

        if (loginResp.statusCode() == SC_OK) {
            int courierId = loginResp.jsonPath().getInt("id");
            deleteCourier(courierId);
        }
    }

}
