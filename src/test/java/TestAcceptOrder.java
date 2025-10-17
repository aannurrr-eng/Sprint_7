import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.AcceptOrder;
import model.CreateCourier;
import model.Order;
import org.junit.*;

import static data.CourierTestData.*;
import static data.OrderTestData.*;
import static data.OrderTestData.COMMENT;
import static data.OrderTestData.DELIVERY_DATE;
import static data.OrderTestData.METRO_STATION;
import static data.OrderTestData.PHONE;
import static data.OrderTestData.RENT_TIME;
import static steps.CourierSteps.*;
import static steps.OrderSteps.*;

public class TestAcceptOrder extends BaseAPITest{

    private static int courierId;
    private int track;
    private int id;

    @BeforeClass
    public static void globalSetup()
    {
        CreateCourier courier = new CreateCourier(genLogin(), genPassword(), genFirstName());
        createCourier(courier);
        courierId = courierId(courier);
    }

    @Before
    public void setup()
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
        id = getOrderIdByTrack(track);
    }

    @AfterClass
    public static void globalTearDown()
    {
        deleteCourier(courierId);
    }

    @After
    public void tearDown()
    {
        cancelOrder(track);
    }

    @Test
    @DisplayName("Check acceptance of order")
    @Description("Check successful acceptance of order")
    public void checkAcceptOrder()
    {
        AcceptOrder aOrder = new AcceptOrder(id, courierId);
        Response response = acceptOrder(aOrder);
        checkAcceptance(response);
    }

    @Test
    @DisplayName("Acceptance of order without id")
    @Description("Check response when accept order without id")
    public void acceptanceOrderWithoutId()
    {
        AcceptOrder aOrder = new AcceptOrder(0, courierId);
        Response response = acceptOrderWithoutId(aOrder);
        checkResponseWhenAcceptanceWithoutData(response);
    }

    @Test
    @DisplayName("Acceptance of order without courierId")
    @Description("Check response when accept order without courierId")
    public void acceptanceOrderWithoutCourierId()
    {
        AcceptOrder aOrder = new AcceptOrder(id, 0);
        Response response = acceptOrderWithoutCourierId(aOrder);
        checkResponseWhenAcceptanceWithoutData(response);
    }

    @Test
    @DisplayName("Acceptance of order with wrong id")
    @Description("Check response when accept order with wrong id")
    public void acceptanceOrderWithWrongId()
    {
        AcceptOrder aOrder = new AcceptOrder(-1, courierId);
        Response response = acceptOrder(aOrder);
        checkResponseWhenAcceptanceWithWrongId(response);
    }

    @Test
    @DisplayName("Acceptance of order with wrong courierId")
    @Description("Check response when accept order with wrong courierId")

    public void acceptanceOrderWithWrongCourierId()
    {
        AcceptOrder aOrder = new AcceptOrder(id, -1);
        Response response = acceptOrder(aOrder);
        checkResponseWhenAcceptanceWithWrongCourierId(response);
    }

    @Test
    @DisplayName("Acceptance of accepted order")
    @Description("Check response when accept accepted order")
    public void acceptanceAcceptedOrder()
    {
        AcceptOrder aOrder = new AcceptOrder(id, courierId);
        acceptOrder(aOrder);
        Response response = acceptOrder(aOrder);
        checkResponseWhenAcceptanceWithAcceptedOrder(response);
    }
}
