package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.AcceptOrder;
import model.Order;

import java.net.HttpURLConnection;

import static data.EndPoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class OrderSteps {

    private static boolean orderCreated = false;
    public static void setOrderCreated(boolean created)
    {
        orderCreated = created;
    }
    public static boolean getOrderCreated()
    {
        return orderCreated;
    }

    @Step("Create order: send post request to /api/v1/orders")
    public static Response createOrder(Order order)
    {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER);
        if (!orderCreated)
            orderCreated = (response.getStatusCode() == HttpURLConnection.HTTP_CREATED);
        return response;
    }

    @Step("Create order: send post request to /api/v1/orders")
    public static int createOrderAndGetTrack(Order order)
    {
        return createOrder(order)
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
                .put(CANCEL_ORDER);
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
                .get(GET_ORDERS);
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
                .put(String.format(ACCEPT_ORDER, order.getId()));
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
                .get(GET_ORDER_BY_TRACK);
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
                .get(GET_ORDER_BY_TRACK);
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
                .put(ACCEPT_ORDER_NULL_ID);
    }

    @Step("Accept order without courierId: send put request to /api/v1/orders/accept/:id")
    public static Response acceptOrderWithoutCourierId(AcceptOrder order)
    {
        return given()
                .contentType(ContentType.JSON)
                .put(String.format(ACCEPT_ORDER, order.getId()));
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
