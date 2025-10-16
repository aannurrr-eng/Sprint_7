package data;

public class EndPoints {
    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static final String DELETE_COURIER = "/api/v1/courier/%d";
    public static final String DELETE_COURIER_NULL_ID = "/api/v1/courier/null";

    public static final String CREATE_ORDER = "/api/v1/orders";
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";
    public static final String GET_ORDERS = "/api/v1/orders";
    public static final String ACCEPT_ORDER = "/api/v1/orders/accept/%d";
    public static final String ACCEPT_ORDER_NULL_ID = "/api/v1/orders/accept/null";
    public static final String GET_ORDER_BY_TRACK = "/api/v1/orders/track";
    }
