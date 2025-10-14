import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.OrderTestData.*;
import static steps.OrderSteps.*;

@RunWith(Parameterized.class)
public class TestCreateOrder extends BaseAPITest {

    private String[] color;

    public TestCreateOrder(String[] color)
    {
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getData()
    {
        return new Object[][]{
                {new String[] {"BLACK"}},
                {new String[] {"GREY"}},
                {new String[] {"BLACK", "GREY"}},
                {null}
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
        int track = checkCreation(response);
        cancelOrder(track);
    }
}
