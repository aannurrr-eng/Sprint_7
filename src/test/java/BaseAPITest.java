import io.restassured.RestAssured;
import org.junit.Before;

public class BaseAPITest{
    @Before
    public void startUp()
    {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

}
