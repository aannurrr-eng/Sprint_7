package model;

public class AcceptOrder {
    private int id;
    private int courierId;

    public AcceptOrder(int id, int courierId) {
        this.id = id;
        this.courierId = courierId;
    }

    public int getId()
    {
        return id;
    }

    public int getCourierId()
    {
        return courierId;
    }

}
