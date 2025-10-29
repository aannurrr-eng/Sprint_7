import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static data.OrderTestData.*;
import static data.OrderTestData.COLOR;
import static data.OrderTestData.COMMENT;
import static data.OrderTestData.DELIVERY_DATE;
import static data.OrderTestData.METRO_STATION;
import static data.OrderTestData.PHONE;
import static data.OrderTestData.RENT_TIME;
import static steps.OrderSteps.*;

public class TestGetOrder extends BaseAPITest {

    private static int track;

    @BeforeClass
    public static void globalSetup()
    {
        Order order = new Order.Builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withAddress(ADDRESS)
                .withMetroStation(METRO_STATION)
                .withPhone(PHONE)
                .withRentTime(RENT_TIME)
                .withDeliveryDate(DELIVERY_DATE)
                .withComment(COMMENT)
                .withColor(COLOR)
                .build();
        track = createOrderAndGetTrack(order);
    }

    @AfterClass
    public static void globalTearDown()
    {
        cancelOrder(track);
    }

    @Test
    @DisplayName("Check getting order")
    @Description("Check successful getting of order")
    public void checkGettingOrder()
    {
        Response response = getOrderByTrack(track);
        checkResponseOfGettingOrder(response);
    }

    @Test
    @DisplayName("Check getting order without track")
    @Description("Check response when get order without track")
    public void gettingOrderWithoutTrack()
    {
        Response response = getOrderWithoutTrack();
        checkResponseWhenGettingOrderWithoutTrack(response);
    }

    @Test
    @DisplayName("Check getting order with wrong track")
    @Description("Check response when get order with wrong track")
    public void gettingOrderWithWrongTrack()
    {
        Response response = getOrderByTrack(1);
        checkResponseWhenGettingOrderWithWrongTrack(response);
    }
}
