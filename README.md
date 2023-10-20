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