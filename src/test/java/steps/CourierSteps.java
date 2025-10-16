package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CreateCourier;
import model.IdCourier;
import model.LoginCourier;

import java.net.HttpURLConnection;

import static data.EndPoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;


public class CourierSteps {

    private static boolean courierCreated = false;

    public static boolean getCourierCreated()
    {
        return courierCreated;
    }

    public static void setCourierCreated(boolean created)
    {
        courierCreated = created;
    }

    @Step("Create courier: send post request to /api/v1/courier")
    public static Response createCourier(CreateCourier courier)
    {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER);
        if (!courierCreated)
            courierCreated = (response.getStatusCode() == HttpURLConnection.HTTP_CREATED);
        return response;
    }

    @Step("Check status code and body of response")
    public static void checkCreation(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_CREATED)
            .body("ok", equalTo(true));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenCreationWithExistingLogin(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_CONFLICT)
            .body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenCreationWithoutData(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Login courier: send post request to /api/v1/courier/login")
    public static Response loginCourier(LoginCourier courier)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Check successful login and containing of 'id' key in response")
    public static void checkResponseOfLoginCourier(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_OK)
            .body("$", hasKey("id"));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenLoginCourierWithWrongData(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
            .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenLoginCourierWithoutData(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Login courier and obtain id")
    public static int courierId(LoginCourier courier)
    {
        return loginCourier(courier)
                .body()
                .path("id");
    }

    @Step("Login courier and obtain id")
    public static int courierId(CreateCourier courier)
    {
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        return courierId(loginCourier);
    }

    @Step("Delete courier: send delete request to /api/v1/courier/:id")
    public static Response deleteCourier(int id)
    {
        IdCourier courier = new IdCourier(String.valueOf(id));
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .delete(String.format(DELETE_COURIER, id));
    }

    @Step("Check status code and body of response")
    public static void checkDelete(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_OK)
            .body("ok", equalTo(true));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenDeleteCourierWithWrongId(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
            .body("message", equalTo("Курьера с таким id нет"));
    }

    @Step("Delete courier without id: send delete request to /api/v1/courier/null")
    public static Response deleteCourier()
    {
        IdCourier courier = new IdCourier("");
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .delete(DELETE_COURIER_NULL_ID);
    }

    @Step("Check status code and message of request")
    public static void checkDeleteCourierWithoutId(Response response)
    {
        response
            .then()
            .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Step("Delete courier")
    public static void deleteCourier(CreateCourier courier)
    {
        int id = courierId(courier);
        deleteCourier(id);
    }

}
