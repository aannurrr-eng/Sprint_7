package data;

import com.github.javafaker.Faker;

public class CourierTestData {

    public static String genLogin()
    {
        Faker courier = new Faker();
        return courier.name().lastName() + courier.regexify("[0-9]{3}");
    }

    public static String genPassword()
    {
        Faker courier = new Faker();
        return courier.regexify("[0-9]{4}");
    }

    public static String genFirstName()
    {
        Faker courier = new Faker();
        return courier.name().firstName();
    }

}
