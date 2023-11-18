package com.ims.constants;

/**
 * List of response messages, which will be used in OrderController and OrderControllerTests
 * The class should not be "instantiable", hence the private constructor.
 */
public final class OrderMessages {

  private OrderMessages() { // prevent instantiation
  }

  // Messages for Order
  public static final String ORDER_CREATE_SUCCESS = "Order Created Successfully";
  public static final String ORDER_UPDATE_SUCCESS = "Order Updated Successfully";
  public static final String ORDER_DELETE_SUCCESS = "Order Deleted Successfully";
  public static final String ORDER_INVALID_ID = "Invalid order id";
  public static final String ORDER_RETRIEVE_FAILURE = "No order found.";
  public static final String ORDER_RETRIEVE_FAILURE_CLIENT = "No orders found for the given client.";
  public static final String ORDER_RETRIEVE_FAILURE_ITEM = "No orders found for the given item.";

  // Messages for OrderDetail
  public static final String ORDER_DETAIL_CREATE_SUCCESS = "Order detail Created Successfully";
  public static final String ORDER_DETAIL_UPDATE_SUCCESS = "Order detail Updated Successfully";
  public static final String ORDER_DETAIL_DELETE_SUCCESS = "Order detail Deleted Successfully";
  public static final String ORDER_DETAIL_INVALID_ID = "Invalid order detail id";
  public static final String ORDER_DETAIL_RETRIEVE_FAILURE = "Order detail retrieve failed.";
}
