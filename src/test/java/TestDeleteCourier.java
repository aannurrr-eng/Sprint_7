import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourier;
import org.junit.Test;

import static data.CourierTestData.*;
import static steps.CourierSteps.*;

public class TestDeleteCourier extends BaseAPITest {

    @Test
    @DisplayName("Check delete of courier")
    @Description("Courier with correct id should be deleted")
    public void checkDeleteCourier()
    {
        CreateCourier courier = new CreateCourier(genLogin(), genPassword(), genFirstName());
        createCourier(courier);
        int id = courierId(courier);
        Response response = deleteCourier(id);
        checkDelete(response);
    }

    @Test
    @DisplayName("Delete courier without id")
    @Description("Courier without id cannot be deleted")
    public void deleteCourierWithoutId()
    {
        Response response = deleteCourier();
        checkDeleteCourierWithoutId(response);
    }

    @Test
    @DisplayName("Delete courier with wrong id")
    @Description("Courier with wrong id cannot be deleted")
    public void deleteCourierWithWrongId()
    {
        Response response = deleteCourier(-1);
        checkResponseWhenDeleteCourierWithWrongId(response);
    }
}
