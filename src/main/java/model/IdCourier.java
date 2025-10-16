package model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IdCourier {
    private String id;

    public int getId()
    {
        return Integer.parseInt(id);
    }
}
