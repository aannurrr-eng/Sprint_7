import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.OrderTestData.*;
import static steps.OrderSteps.*;

@RunWith(Parameterized.class)
public class TestCreateOrder extends BaseAPITest {

    private int track;

    @Before
    public void setup()
    {
        setOrderCreated(false);
    }

    @After
    public void tearDown()
    {
        if (!getOrderCreated())
            return;
        cancelOrder(track);
    }

    private String colorName;
    private String[] color;

    public TestCreateOrder(String colorName, String[] color)
    {
        this.colorName = colorName; this.color = color;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getData()
    {
       return new Object[][]{
                {"BLACK", new String[] {"BLACK"}},
                {"GREY", new String[] {"GREY"}},
                {"BLACK, GREY", new String[] {"BLACK", "GREY"}},
                {"null", null}
        };
    }

    @Test
    @DisplayName("Check order creation with different colors")
    @Description("Orders with different colors should be created")
    public void checkCreateOrder()
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
                .withColor(color)
                .build();
        Response response = createOrder(order);
        track = checkCreation(response);
    }
}
