package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.AcceptOrder;
import model.Order;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class OrderSteps {

    @Step("Create order: send post request to /api/v1/orders")
    public static Response createOrder(Order order)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Create order: send post request to /api/v1/orders")
    public static int createOrderAntGetTrack(Order order)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract()
                .path("track");
    }

    @Step("Cancel order: send put request to /api/v1/orders")
    public static void cancelOrder(int track)
    {
        given()
                .contentType(ContentType.JSON)
                .queryParam("track", track)
                .put("/api/v1/orders/cancel");
    }

    @Step("Check response code of creation and containing of 'track' key ")
    public static int checkCreation(Response response)
    {
        return response.then()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("$", hasKey("track"))
                .extract().body().path("track");
    }

    @Step("Send get request to /api/v1/orders")
    public static Response getOrderList()
    {
        return given()
                .get("/api/v1/orders");
    }

    @Step("Check response has order list")
    public static void checkResponseContainsOrderList(Response response) {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("$", hasKey("orders"));
    }

    @Step("Accept order: send put request to /api/v1/orders/accept/:id")
    public static Response acceptOrder(AcceptOrder order)
    {
        return given()
                .contentType(ContentType.JSON)
                .queryParam("courierId", order.getCourierId())
                .put(String.format("/api/v1/orders/accept/%d", order.getId()));
    }

    @Step("Check status code and body of response")
    public static void checkAcceptance(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("ok", equalTo(true));
    }

    @Step("Get order: send get request to /api/v1/orders/track")
    public static Response getOrderByTrack(int track)
    {
        return given()
                .contentType(ContentType.JSON)
                .queryParam("t", track)
                .get("/api/v1/orders/track");
    }

    @Step("Check status code and body of request")
    public static void checkResponseOfGettingOrder(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("$", hasKey("order"));
    }

    @Step("Get order without track: send get request to /api/v1/orders/track")
    public static Response getOrderWithoutTrack()
    {
        return given()
                .contentType(ContentType.JSON)
                .get("/api/v1/orders/track");
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenGettingOrderWithoutTrack(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Step("Check status code and message of response")
    public static void checkResponseWhenGettingOrderWithWrongTrack(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Заказ не найден"));
    }

    @Step("Get order id by order track")
    public static int getOrderIdByTrack(int track)
    {
        return getOrderByTrack(track)
                .then()
                .extract()
                .body()
                .path("order.id");
    }

    @Step("Accept order without id: send put request to /api/v1/orders/accept/null")
    public static Response acceptOrderWithoutId(AcceptOrder order)
    {
        return given()
                .contentType(ContentType.JSON)
                .queryParam("courierId", order.getCourierId())
                .put("/api/v1/orders/accept/null");
    }

    @Step("Accept order without courierId: send put request to /api/v1/orders/accept/:id")
    public static Response acceptOrderWithoutCourierId(AcceptOrder order)
    {
        return given()
                .contentType(ContentType.JSON)
                .put(String.format("/api/v1/orders/accept/%d", order.getId()));
    }

    @Step("Check response when accept order without data")
    public static void checkResponseWhenAcceptanceWithoutData(Response response)
    {
        response

                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Step("Check response when accept order with wrong id")
    public static void checkResponseWhenAcceptanceWithWrongId(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Заказа с таким id не существует"));
    }

    @Step("Check response when accept order with wrong courierId")
    public static void checkResponseWhenAcceptanceWithWrongCourierId(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Step("Check response when accept accepted order")
    public static void checkResponseWhenAcceptanceWithAcceptedOrder(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот заказ уже в работе"));
    }
}
