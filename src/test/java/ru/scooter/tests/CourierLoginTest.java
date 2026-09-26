package ru.scooter.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.scooter.models.CourierModel;

import static org.hamcrest.Matchers.notNullValue;
import static ru.scooter.data.CourierData.*;
import static ru.scooter.steps.CourierSteps.*;

public class CourierLoginTest {

    @Test
    @DisplayName("Check a courier can log in")
    @Description("The test checks whether the created courier can be authorized")
    public void authorizationCourierSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        loginCourier(new CourierModel(LOGIN, PASSWORD))
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Check impossible of logging in without a login")
    @Description("The test checks the handling of incorrect requests")
    public void authorizationWithoutLoginFails() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        loginCourier(new CourierModel(null, PASSWORD))
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check impossible of logging in without a password")
    @Description("The test checks the handling of incorrect requests")
    public void authorizationWithoutPasswordFails() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        loginCourier(new CourierModel(LOGIN, null))
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Error check if the login is entered incorrectly")
    @Description("The test checks the handling of incorrect login")
    public void authorizationWithIncorrectLoginFails() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        loginCourier(new CourierModel(LOGIN + "e", PASSWORD))
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    @DisplayName("Error check if the password is entered incorrectly")
    @Description("The test checks the handling of incorrect login")
    public void authorizationWithIncorrectPasswordFails() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);

        loginCourier(new CourierModel(LOGIN, PASSWORD + "e"))
                .then()
                .log().all()
                .statusCode(404);
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
