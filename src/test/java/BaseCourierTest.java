import model.CreateCourier;
import org.junit.After;
import org.junit.Before;

import static steps.CourierSteps.*;
import static steps.CourierSteps.deleteCourier;

public class BaseCourierTest extends BaseAPITest{

    protected CreateCourier courier;

    @Before
    public void startUp()
    {
        setCourierCreated(false);
        super.startUp();
    }

    @After
    public void tearDown()
    {
        if (!getCourierCreated())
            return;
        deleteCourier(courier);
    }
}
