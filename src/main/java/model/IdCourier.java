package model;

public class IdCourier {
    private String id;

    public IdCourier(String id) {
        this.id = id;
    }

    public int getId()
    {
        return Integer.parseInt(id);
    }
}
