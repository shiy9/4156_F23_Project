package com.ims.constants;

/**
 * List of response messages, which will be used in UserController and UserControllerTests
 * The class should not be "instantiable", hence the private constructor.
 */
public final class ItemMessages {
  private ItemMessages() { // prevent instantiation
  }

  public static final String INVALID_ITEM_ID = "Invalid item id";

  public static final String INVALID_LOCATION_ID = "Invalid location id";

  public static final String INVALID_ZIP_CODE = "Invalid zip code";

  public static final String INSERT_SUCCESS = "Insert successful";

  public static final String INSERT_FAILURE = "Insert failed";

  public static final String UPDATE_SUCCESS = "Update successful";

  public static final String UPDATE_FAILURE = "Update failed";

  public static final String BARCODE_ALREADY_EXISTS = "Barcode already exists";

  public static final String BARCODE_GENERATION_SUCCESS = "Barcode generation successful";

  public static final String BARCODE_GENERATION_FAILURE = "Barcode generation failed";

  public static final String BARCODE_GENERATION_ERROR = "Error occurred during Barcode generation";

  public static final String OUT_OF_CURRENT_STOCK_LEVEL = "Out of current stock level";

  public static final String OUT_OF_QUANTITY_AT_LOCATION = "Out of quantity at location";

  public static final String INVALID_ITEM_ID_OR_LOCATION_ID = "Invalid item id or location id";
}