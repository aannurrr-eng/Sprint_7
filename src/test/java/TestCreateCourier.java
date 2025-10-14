import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourier;
import org.junit.Test;


import static data.CourierTestData.*;
import static steps.CourierSteps.*;

public class TestCreateCourier extends BaseCourierTest {

    @Test
    @DisplayName("Check courier successful creation")
    @Description("Create courier with correct data")
    public void checkCourierCreation()
    {
        courier = new CreateCourier(genLogin(), genPassword(), genFirstName());
        Response response = createCourier(courier);
        checkCreation(response);
    }

    @Test
    @DisplayName("Check courier without firstName successful creation")
    @Description("Create courier without firstName")
    public void checkCourierWithoutFirstNameCreation()
    {
        courier = new CreateCourier(genLogin(), genPassword(), "");
        Response response = createCourier(courier);
        checkCreation(response);
    }

    @Test
    @DisplayName("Check correct response for creation courier with existing login")
    @Description("Courier with existing login cannot be created")
    public void courierWithExistingLoginCreation()
    {
        courier = new CreateCourier(genLogin(), genPassword(), genFirstName());
        createCourier(courier);
        Response response = createCourier(courier);
        checkResponseWhenCreationWithExistingLogin(response);
    }

    @Test
    @DisplayName("Check correct response for creation courier without login")
    @Description("Courier without login cannot be created")
    public void courierWithoutLoginCreation()
    {
        courier = new CreateCourier("", genPassword(), genFirstName());
        Response response = createCourier(courier);
        checkResponseWhenCreationWithoutData(response);
    }

    @Test
    @DisplayName("Check correct response for creation courier without password")
    @Description("Courier without password cannot be created")
    public void courierWithoutPasswordCreation()
    {
        courier = new CreateCourier(genLogin(), "", genFirstName());
        Response response = createCourier(courier);
        checkResponseWhenCreationWithoutData(response);
    }

    @Test
    @DisplayName("Check correct response for creation courier without login and password")
    @Description("Courier without login and password cannot be created")
    public void courierWithoutLoginAndPasswordCreation()
    {
        courier = new CreateCourier("", "", genFirstName());
        Response response = createCourier(courier);
        checkResponseWhenCreationWithoutData(response);
    }

}
