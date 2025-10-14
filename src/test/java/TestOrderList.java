import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static steps.OrderSteps.checkResponseContainsOrderList;
import static steps.OrderSteps.getOrderList;

public class TestOrderList extends BaseAPITest{

    @Test
    @DisplayName("Check getting of order list")
    @Description("Getting of order list should return list of orders")
    public void checkGettingOrderList()
    {
        Response response = getOrderList();
        checkResponseContainsOrderList(response);
    }
}
