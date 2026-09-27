package ru.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.scooter.client.ApiClient;
import ru.scooter.models.CourierModel;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static ru.scooter.data.CourierData.COURIER_CREATE_PATH;
import static ru.scooter.data.CourierData.COURIER_LOGIN_PATH;

public class CourierSteps {

    @Step("Send POST request to /api/v1/courier")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH);
    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(CourierModel courier) {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(courier)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Deleting a courier by Id")
    public static Response deleteCourier(int courierId) {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(Collections.singletonMap("id", Integer.toString(courierId)))
                .when()
                .delete(COURIER_CREATE_PATH + "/" + courierId);
    }

    @Step("Deleting a courier without Id")
    public static Response deleteCourier() {
        return given()
                .log().all()
                .spec(ApiClient.requestSpec())
                .body(Collections.singletonMap("id", null))
                .when()
                .delete(COURIER_CREATE_PATH);
    }
}
