# Team iheartapi COMS4156 Fall 23 Project
### Team Members
- Ziran Liu zl3234
- Bill Shi ys3595
- Chengyu Wang cw3512
- Jasmine Xin yx2810
- Xirui Yao xy2571

## Getting Started
- `git clone` the project.
- `Open` the project with IntelliJ IDE. The IDE should be able to detect the Maven configuration
file (`pom.xml`) and prompt to configure Maven at the bottom right corner. Click the button to configure Maven.
  - Alternatively, run `mvn clean install` to install all needed dependencies to run the project
    (although the IDE configuration has always worked for our team).
- To start the application and run Postman requests, start the `ImsApplication` on the top right
corner of the IDE, which will start running the server at `http://localhost:8001`.
  - Alternatively, right click on `ims/src/main/java/com/ims/ImsApplication.java` and click the run button.

## Testing
- Unit tests are in `ims/src/test/java/com/ims/`
- To run all tests, in the IntelliJ IDE, right click on `ims/src/test/java/com/ims/` and click `Run Tests in ims`. This will currently give a nicer interface than the method below, although the command can be configured in later iterations.
  - Or, `cd` into the `ims` directory, and run `mvn test` in the terminal. (We need to `cd` into the directory 
    where the Maven configuration file `pom.xml` resides, or the command will fail.)
    

## API Endpoints
### User-related
`/user/register`
- **Method**: POST
- **Request Body**: JSON containing user `email` and `password`
- **Expected Response**:
  - `201`: registration successful, and user is logged into the database
  - `400`: registration failed (maybe due to email already in-use, invalid email and password formats. See response body for more information)

`/user/login`
- **Method**: POST
- **Request Body**: JSON containing user `email` and `password`
- **Expected Response**:
    - `200`: login success
    - `401`: login failed, password mismatch
    - `404`: login failed, user does not exist
- Note: in future iterations, we plan to have `login` return a stateful token and will be used to modify 
database (such as adding an order, etc.). We will also support a `/user/logout` endpoint, which will
invalidate the token.

&nbsp;
<br>

### Order-related
#### **Create an Order**
`/order/create`
- **Method**: POST
- **Request Body**: JSON containing order details (orderId, userId, type, orderDate, orderStatus)
- **Expected Response**:
  - `200`: Order Created Successfully

#### **Update an Order**
`/order/update`
- **Method**: PUT
- **Request Body**: JSON containing order details to be updated (orderId must be present)
- **Expected Response**:
  - `200`: Order Updated Successfully

#### **Delete an Order**
`/order/delete/{orderId}`
- **Method**: POST
- **Request Body**:  {orderId} - The ID of the order to be deleted
- **Expected Response**:
  - `200`: Order Deleted Successfully

#### **Retrieve Orders by User ID**
`/order/retrieve/user/{userId}`
- **Method**: GET
- **Request Body**:  {userId} - The ID of the user to retrieve orders for
- **Expected Response**:
  - - `200`: List of orders associated with the provided user ID
    - `404`: No orders found for the provided user ID

&nbsp;
<br>

### Item Related
`/location/create`
- **Method**: POST
- **Request Body**: JSON containing location details (locationId, name, address1, address2, locationType, userId)
- **Expected Response**:
  - `200`: Insert successful
  - `400`: Insert failed

`/location/get/{id}`
- **Method**: GET
- **Request Body**:  {id} - The locationID of the location to retrieve
- **Expected Response**:
  - `200`: Location
  - `404`: Not Found

`/item/create`
- **Method**: POST
- **Request Body**: JSON containing item details (itemId, userId, name, description, price, current_stock_level, barcode)
- **Expected Response**:
  - `200`: Insert successful
  - `400`: Insert failed

`/item/get/{id}`
- **Method**: GET
- **Request Body**:  {id} - The itemID of the item to retrieve
- **Expected Response**:
  - `200`: Item
  - `404`: Not Found

`/item/getByUserId/{userId}`
- **Method**: GET
- **Request Body**:  {userId} - The userID of the user to retrieve items for
- **Expected Response**:
  - `200`: List of Items
  - `404`: Not Found

`/item/update`
- **Method**: POST
- **Request Body**: JSON containing item details to be updated (itemId must be present)
- **Expected Response**:
  - `200`: Update successful
  - `400`: Update failed

`/item/generateBarcode/{id}`
- **Method**: GET
- **Request Body**:  {id} - The itemID of the item to generate a barcode for
- **Expected Response**:
  - `200`: Generation success
  - `400`: Generation failure

`/item/barcode/{id}`
- **Method**: GET
- **Request Body**:  {id} - The itemID of the item to retrieve the barcode for
- **Expected Response**:
  - `200`: The barcode in PNG format
  - `404`: Not Found


`/itemLocation/create`
- **Method**: POST
- **Request Body**: JSON containing itemLocation details (itemId, locationId, quantityAtLocation)
- **Expected Response**:
  - `200`: Insert successful
  - `400`: Invalid item id / Invalid location id / Insert failed 
- **Note**: Since we require a valid Item and Location to exist before creating an ItemLocation, there
are several failure messages available here.This endpoint will also update the current_stock_level of the Items table by 
adding the quantityAtLocation to the current_stock_level.

`/itemLocation/get/{itemId}/{locationId}`
- **Method**: GET
- **Request Body**:  
  {itemId} - The itemID of the item to retrieve  
  {locationId} - The locationID of the location to retrieve
- Expected Response:
  - `200`: ItemLocation
  - `404`: Not Found

`/itemLocation/getByLocationId/{locationId}`
- **Method**: GET
- **Request Body**: {locationId} - The locationID of the location to retrieve
- Expected Response:
  - `200`: List of ItemLocations
  - `404`: Not Found


`/itemLocation/getByItemId/{itemId}`
- **Method**: GET
- **Request Body**: {itemId} - The itemID of the item to retrieve
- Expected Response:
  - `200`: List of ItemLocations
  - `404`: Not Found

## Style Checker
- We are using the CheckStyle plugin on IntelliJ to check for potential style warning/errors.

