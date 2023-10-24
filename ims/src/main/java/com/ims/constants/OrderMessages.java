package com.ims.constants;

/**
 * List of response messages, which will be used in OrderController and OrderControllerTests
 * The class should not be "instantiable", hence the private constructor.
 */
public final class OrderMessages {

    private OrderMessages() { // prevent instantiation
    }

    public static final String CREATE_SUCCESS = "Order Created Successfully";
    public static final String UPDATE_SUCCESS = "Order Updated Successfully";
    public static final String DELETE_SUCCESS = "Order Deleted Successfully";
    public static final String RETRIEVE_SUCCESS_USER = "Orders retrieved successfully for the user.";
    public static final String RETRIEVE_SUCCESS_ITEM = "Orders retrieved successfully for the item.";
    public static final String RETRIEVE_FAILURE_USER = "No orders found for the given user.";
    public static final String RETRIEVE_FAILURE_ITEM = "No orders found for the given item.";
}
