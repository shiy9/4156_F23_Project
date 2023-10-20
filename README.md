# Team iheartapi COMS4156 Fall 23 Project
### Team Members
- Ziran Liu zl3234
- Bill Shi ys3595
- Chengyu Wang cw3512
- Jasmine Xin yx2810
- Xirui Yao xy2571

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


### Order-related
#### **Create an Order**
`/order/create`
- **Method**: POST
- **Request Body**: JSON containing order details (orderId, userId, type, itemId, itemLocationId, quantity, orderDate, amount, dueDate, returnDate, orderStatus)
- **Expected Response**:
  - Success message: "Order Created Successfully"

#### **Update an Order**
 `/order/update`
- **Method**: PUT
- **Request Body**: JSON containing order details to be updated (orderId must be present)
- **Expected Response**:
  - Success message: "Order Updated Successfully"

#### **Delete an Order**
  `/order/delete/{orderId}`
- **Method**: POST
- **Request Body**:  {orderId} - The ID of the order to be deleted
- **Expected Response**:
  - Success message: "Order Deleted Successfully"

#### **Retrieve Orders by User ID**
`/order/retrieve/user/{userId}`
- **Method**: GET
- **Request Body**:  {userId} - The ID of the user to retrieve orders for
- **Expected Response**:
  - List of orders associated with the provided user ID

#### **Retrieve Orders by Item ID**
`/order/retrieve/item/{itemId}`
- **Method**: GET
- **Request Body**:  {itemId} - The ID of the item to retrieve orders for
- **Expected Response**:
  List of orders associated with the provided item ID
