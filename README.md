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
### Client-related
`/client/register`
- **Method**: POST
- **Request Body**: JSON containing client `email` and `password`
- **Expected Response**:
  - Response body is a message indicating whether the request succeeded or failed.
  - Response codes:
    - `201`: registration successful, and user is logged into the database
    - `400`: registration failed (maybe due to email already in-use, invalid email and password formats. See response body for more information)

`/client/login`
- **Method**: POST
- **Request Body**: JSON containing client `email` and `password`
- **Expected Response**:
  - Response body is a JSON object with the following structure:
    - ```json
      {
        "message": "message explaining request status",
        "token": "authentication_token"
      }
      ```
    - The response body will NOT contain a token if login was not successful.
  - Response codes:
    - `200`: login success
    - `401`: login failed, password mismatch
    - `404`: login failed, client does not exist
- The application using the service will be responsible for storing the token as it will be 
  needed to complete Order-related and Item-related operations.

&nbsp;
<br>

### Side note: how to use token and expected token behavior
- Client-related endpoints do NOT require a token, since they are responsible for registration 
  and generating the token.
- For all Order-related and Item-related requests, the token acquired from `/client/login` will need
  to be included in the header as a Bearer token (i.e. in the `Authorization` header field, the 
  value needs to be `Bearer <token>`). Note that the `Bearer` and space is necessary, else the 
  request will fail.
  - In Postman, you can click on the "Authorization" tab, select the type to be "Bearer Token", 
    and copy the token into the box.
- Tokens generated at login are valid for 1 hour. If no token is provided when needed, or the 
  token has expired, the request will fail with a `401` unauthorized code with a message saying 
  "Expired or invalid token.".
- Additionally, different `clientType`s may or may not have access to certain endpoints. If a 
  `retail` type client tries to interact with an endpoint only meant for `warehouse` type client,
  a `401` unauthorized code will also be thrown with a message saying "Not authorized to use 
  this endpoint.". More implementation details [here](#details-of-token-related-implementation).


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

## Details of Token-related Implementation
- All classes to support authentication at every endpoint using JWT token are in 
  `ims/src/main/java/com/ims/security/`.
- `TokenUtil` class is a utility class for the token. It is responsible for generating and 
  validating token, extracting client type encoded into the token as well as several other 
  functions. See the code for more details.
- We are using Spring Security to support authentication at every endpoint. Since the token is 
  custom to our service, we need to configure the spring security to meet our needs.
  - `SecurityConfiguration` class does just the configuration. It allows the client-related 
    endpoints to go without needing authentication and run `TokenFilter` on the endpoints that 
    require authentication.
  - All request will first go through `TokenFilter`. After verifying the endpoint is indeed 
    supported, it will verify that the request carries a valid token. `clientType` check does 
    NOT happen at this step as it is endpoint-specific. It does encode an "Authority" based 
    on the `clientType`, which will be used to check if the `clientType` is valid for certain 
    requests. More on this below.
- All endpoints will now have a `@PreAuthorize` in front. For example,
  - If both `retail` and `warehouse` `clientType`s can access the endpoint (note that these two 
    types are coded as constants in `com.ims.constants.ClientConstants`), 
    ```java
    @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
      + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
    ```
  - If only `warehouse` `clientType` can access the endpoint,
    ```java
    @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE)")
    ```
  - Similarly, if only `retail` `clientType` can access the endpoint,
    ```java
    @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
    ```
- If `clientType` mismatch occurred because `@PreAuthorize` failed, an `AccessDeniedException` 
  will be thrown and caught by `ims/src/main/java/com/ims/exception/GlobalExceptionHandler`. A 
  `401` unauthorized will be returned along with a message saying "Not authorized to use this 
  endpoint."


&nbsp;
<br>


## Style Checker
- We are using the CheckStyle plugin on IntelliJ to check for potential style warning/errors.

## References
### Resources used when implementing JWT token
- https://jwt.io/libraries
- https://github.com/jwtk/jjwt
### Resources used when implementing Client-related code
- For [password regex](https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a)
- For [email regex](https://regexr.com/3e48o)
- For password encoder (BCrypt): [link1](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html), [link2](https://www.educative.io/answers/how-does-the-bcrypt-encoding-scheme-work-in-spring-security)
### Resources used for Spring Security
- https://spring.io/projects/spring-security#learn
- https://www.codejava.net/frameworks/spring-boot/spring-security-jwt-authentication-tutorial 