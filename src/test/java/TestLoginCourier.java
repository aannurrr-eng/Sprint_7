import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourier;
import model.LoginCourier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static data.CourierTestData.*;
import static steps.CourierSteps.*;

public class TestLoginCourier extends BaseAPITest{

    private static CreateCourier courier;

    @BeforeClass
    public static void globalSetup()
    {
        courier = new CreateCourier(genLogin(), genPassword(), genFirstName());
        createCourier(courier);
    }

    @AfterClass
    public static void globalTearDown()
    {
        deleteCourier(courier);
    }

    @Test
    @DisplayName("Check successful courier login")
    @Description("Courier with correct data could login")
    public void checkLoginCourier()
    {
        LoginCourier lCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        Response response = loginCourier(lCourier);
        checkResponseOfLoginCourier(response);
    }

    @Test
    @DisplayName("Login of courier with wrong login")
    @Description("Courier with wrong login cannot login")
    public void loginCourierWithWrongLogin()
    {
        LoginCourier lCourier = new LoginCourier(genLogin(), genPassword());
        Response response = loginCourier(lCourier);
        checkResponseWhenLoginCourierWithWrongData(response);
    }

    @Test
    @DisplayName("Login of courier with wrong password")
    @Description("Courier with wrong password cannot login")
    public void loginCourierWithWrongPassword()
    {
        LoginCourier lCourier = new LoginCourier(courier.getLogin(), courier.getPassword() + courier.getPassword());
        Response response = loginCourier(lCourier);
        checkResponseWhenLoginCourierWithWrongData(response);
    }

    @Test
    @DisplayName("Login of courier without login")
    @Description("Courier without login cannot login")
    public void loginCourierWithoutLogin()
    {
        LoginCourier lCourier = new LoginCourier("", courier.getPassword());
        Response response = loginCourier(lCourier);
        checkResponseWhenLoginCourierWithoutData(response);
    }

    @Test
    @DisplayName("Login of courier without password")
    @Description("Courier without password cannot login")
    public void loginCourierWithoutPassword()
    {
        LoginCourier lCourier = new LoginCourier(courier.getLogin(), "");
        Response response = loginCourier(lCourier);
        checkResponseWhenLoginCourierWithoutData(response);
    }
}
