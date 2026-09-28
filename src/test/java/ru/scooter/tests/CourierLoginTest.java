package ru.scooter.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.scooter.models.CourierModel;

import static org.hamcrest.Matchers.*;
import static ru.scooter.data.CourierData.*;
import static ru.scooter.steps.CourierSteps.*;

public class CourierLoginTest {

    @Before
    public void setUp() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
    }

    @Test
    @DisplayName("Check a courier can log in")
    @Description("The test checks whether the created courier can be authorized")
    public void authorizationCourierSuccess() {
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
        loginCourier(new CourierModel(null, PASSWORD))
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(INSUFFICIENT_COURIER_LOGIN));
    }

    @Test
    @DisplayName("Check impossible of logging in without a password")
    @Description("The test checks the handling of incorrect requests")
    public void authorizationWithoutPasswordFails() {
        loginCourier(new CourierModel(LOGIN, null))
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo(INSUFFICIENT_COURIER_LOGIN));
    }

    @Test
    @DisplayName("Error check if the login is entered incorrectly")
    @Description("The test checks the handling of incorrect login")
    public void authorizationWithIncorrectLoginFails() {
        loginCourier(new CourierModel(LOGIN + "e", PASSWORD))
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(ACCOUNT_NOT_FOUND));
    }

    @Test
    @DisplayName("Error check if the password is entered incorrectly")
    @Description("The test checks the handling of incorrect login")
    public void authorizationWithIncorrectPasswordFails() {
        loginCourier(new CourierModel(LOGIN, PASSWORD + "e"))
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo(ACCOUNT_NOT_FOUND));
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
