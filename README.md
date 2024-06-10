# Deal Kh API

### Project Structure

```
├── HELP.md
├── access-refresh-token-keys
│ ├── access-token-private.key
│ ├── access-token-public.key
│ ├── refresh-token-private.key
│ └── refresh-token-public.key
├── build.gradle
├── filestorage
│ └── images
│ ├── 0d8af639-14d2-4639-9a7d-b4782360dc3c.jpg
│ ├── 2bee7011-7789-49f5-b246-50609522cf5c.jpeg
│ ├── 4da77593-ef15-4e80-8fcb-a445a0fc2bb0.jpg
│ ├── 61517628-8984-4c65-9114-dc6101f8b741.jpg
│ ├── 742dcdda-a2c0-4edf-9f49-eca95f731ae9.jpg
│ ├── aa6bf75c-a5c0-4296-b712-0df322eff9b0.jpg
│ ├── bdc10e61-8b58-476a-b771-1144a49d500f.gif
│ ├── be513735-eab0-4047-b420-139253e53b46.jpg
│ ├── c5935ec6-33b2-4be8-9fe0-740d18786d16.jpg
│ └── d912d98b-107d-407d-aef9-48efe176fc4b.jpg
├── gradle
│ └── wrapper
│ ├── gradle-wrapper.jar
│ └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
├── main
│ ├── java
│ │ └── co
│ │ └── istad
│ │ └── dealkh
│ │ ├── DealKhApiApplication.java
│ │ ├── audit
│ │ │ ├── Auditable.java
│ │ │ └── EntityAuditorAware.java
│ │ ├── base
│ │ │ └── BaseResponse.java
│ │ ├── config
│ │ │ ├── AuthProviderConfig.java
│ │ │ ├── SecurityConfiguration.java
│ │ │ ├── SpringDocConfig.java
│ │ │ └── WebMvcConfiguration.java
│ │ ├── converter
│ │ │ ├── ImageListConverter.java
│ │ │ ├── JsonListConverter.java
│ │ │ └── SocialListConverter.java
│ │ ├── domain
│ │ │ ├── Authority.java
│ │ │ ├── Category.java
│ │ │ ├── Discount.java
│ │ │ ├── DiscountType.java
│ │ │ ├── Order.java
│ │ │ ├── Product.java
│ │ │ ├── ProductFeedback.java
│ │ │ ├── ProductRating.java
│ │ │ ├── ResetPassword.java
│ │ │ ├── Role.java
│ │ │ ├── Shop.java
│ │ │ ├── ShopRating.java
│ │ │ ├── ShopReport.java
│ │ │ ├── ShopType.java
│ │ │ ├── User.java
│ │ │ ├── WishList.java
│ │ │ └── json
│ │ │ ├── Image.java
│ │ │ └── SocialMedia.java
│ │ ├── exception
│ │ │ └── GlobalRestControllerAdviser.java
│ │ ├── features
│ │ │ ├── auth
│ │ │ │ ├── AuthController.java
│ │ │ │ ├── AuthService.java
│ │ │ │ ├── AuthServiceImpl.java
│ │ │ │ └── dto
│ │ │ │ ├── AuthRequest.java
│ │ │ │ ├── AuthResponse.java
│ │ │ │ └── RefreshTokenRequest.java
│ │ │ ├── authority
│ │ │ │ └── AuthorityRepository.java
│ │ │ ├── category
│ │ │ │ ├── CategoryRepository.java
│ │ │ │ ├── CategoryService.java
│ │ │ │ ├── CategoryServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── CategoryCreateRequest.java
│ │ │ │ │ ├── CategoryResponse.java
│ │ │ │ │ └── CategoryUpdateRequest.java
│ │ │ │ └── web
│ │ │ │ └── CategoryController.java
│ │ │ ├── discount
│ │ │ │ ├── DiscountRepository.java
│ │ │ │ ├── DiscountService.java
│ │ │ │ ├── DiscountServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── DiscountCreateRequest.java
│ │ │ │ │ ├── DiscountTypeResponse.java
│ │ │ │ │ └── DiscountUpdateRequest.java
│ │ │ │ └── web
│ │ │ │ └── DiscountController.java
│ │ │ ├── discounttype
│ │ │ │ └── DiscountTypeRepository.java
│ │ │ ├── image
│ │ │ │ ├── ImageService.java
│ │ │ │ ├── ImageServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ImageRequest.java
│ │ │ │ │ ├── ImageResponse.java
│ │ │ │ │ └── ImageUploadResponse.java
│ │ │ │ └── web
│ │ │ │ └── ImageController.java
│ │ │ ├── order
│ │ │ │ ├── OrderRepository.java
│ │ │ │ ├── OrderService.java
│ │ │ │ ├── OrderServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── OrderRequest.java
│ │ │ │ │ └── OrderResponse.java
│ │ │ │ └── web
│ │ │ │ └── OrderController.java
│ │ │ ├── product
│ │ │ │ ├── ProductRepository.java
│ │ │ │ ├── ProductService.java
│ │ │ │ ├── ProductServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ProductCreateRequest.java
│ │ │ │ │ ├── ProductResponse.java
│ │ │ │ │ └── ProductUpdateRequest.java
│ │ │ │ └── web
│ │ │ │ └── ProductController.java
│ │ │ ├── productfeedback
│ │ │ │ ├── ProductFeedbackRepository.java
│ │ │ │ ├── ProductFeedbackService.java
│ │ │ │ ├── ProductFeedbackServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ProductFeedbackRequest.java
│ │ │ │ │ └── ProductFeedbackResponse.java
│ │ │ │ └── web
│ │ │ │ └── ProductFeedbackController.java
│ │ │ ├── productrating
│ │ │ │ ├── ProductRatingRepository.java
│ │ │ │ ├── ProductRatingService.java
│ │ │ │ ├── ProductRatingServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ProductRatingRequest.java
│ │ │ │ │ └── ProductRatingResponse.java
│ │ │ │ └── web
│ │ │ │ └── ProductRatingController.java
│ │ │ ├── role
│ │ │ │ └── RoleRepository.java
│ │ │ ├── shop
│ │ │ │ ├── ShopRepository.java
│ │ │ │ ├── ShopService.java
│ │ │ │ ├── ShopServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ShopRequest.java
│ │ │ │ │ └── ShopResponse.java
│ │ │ │ └── web
│ │ │ │ └── ShopController.java
│ │ │ ├── shoptype
│ │ │ │ ├── ShopTypeRepository.java
│ │ │ │ ├── ShopTypeService.java
│ │ │ │ ├── ShopTypeServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── ShopTypeCreateRequest.java
│ │ │ │ │ ├── ShopTypeRequest.java
│ │ │ │ │ ├── ShopTypeResponse.java
│ │ │ │ │ └── ShopTypeUpdateRequest.java
│ │ │ │ └── web
│ │ │ │ └── ShopTypeController.java
│ │ │ ├── user
│ │ │ │ ├── UserRepository.java
│ │ │ │ ├── UserService.java
│ │ │ │ ├── UserServiceImpl.java
│ │ │ │ ├── dto
│ │ │ │ │ ├── RoleResponse.java
│ │ │ │ │ ├── UpdateProfileRequest.java
│ │ │ │ │ ├── UpdateRoleRequest.java
│ │ │ │ │ ├── UserCreateRequest.java
│ │ │ │ │ ├── UserProfileRequest.java
│ │ │ │ │ ├── UserProfileResponse.java
│ │ │ │ │ ├── UserRequest.java
│ │ │ │ │ ├── UserResetPasswordRequest.java
│ │ │ │ │ ├── UserResponse.java
│ │ │ │ │ ├── UserRoleRequest.java
│ │ │ │ │ ├── UserShopResponse.java
│ │ │ │ │ ├── UserUpdatePasswordRequest.java
│ │ │ │ │ ├── UserUpdateRequest.java
│ │ │ │ │ └── UserWishResponse.java
│ │ │ │ └── web
│ │ │ │ └── UserController.java
│ │ │ └── wishlist
│ │ │ ├── WishListRepository.java
│ │ │ ├── WishListService.java
│ │ │ ├── WishListServiceImpl.java
│ │ │ ├── dto
│ │ │ │ ├── WishListRequest.java
│ │ │ │ └── WishListResponse.java
│ │ │ └── web
│ │ │ └── WishListController.java
│ │ ├── init
│ │ │ └── DataInit.java
│ │ ├── mapper
│ │ │ ├── CategoryMapper.java
│ │ │ ├── CustomMapper.java
│ │ │ ├── DiscountMapper.java
│ │ │ ├── OrderMapper.java
│ │ │ ├── ProductFeedbackMapper.java
│ │ │ ├── ProductMapper.java
│ │ │ ├── ProductRatingMapper.java
│ │ │ ├── ShopMapper.java
│ │ │ ├── ShopTypeMapper.java
│ │ │ ├── UserMapper.java
│ │ │ └── WishListMapper.java
│ │ ├── paging
│ │ │ ├── PageResponse.java
│ │ │ ├── Pagination.java
│ │ │ └── PaginationResponse.java
│ │ ├── security
│ │ │ ├── CustomAuthenticationProvider.java
│ │ │ ├── CustomUserDetails.java
│ │ │ ├── JwtToUserConverter.java
│ │ │ ├── KeyUtils.java
│ │ │ ├── TokenGenerator.java
│ │ │ └── UserDetailsServiceImpl.java
│ │ ├── specification
│ │ │ └── filter
│ │ │ ├── DiscountFilter.java
│ │ │ ├── DiscountSpecification.java
│ │ │ ├── PageFilter.java
│ │ │ ├── ProductFilter.java
│ │ │ ├── ProductSpecification.java
│ │ │ ├── ShopTypeFilter.java
│ │ │ ├── ShopTypeSpecification.java
│ │ │ ├── UserFilter.java
│ │ │ └── UserSpecification.java
│ │ └── validator
│ │ ├── discount
│ │ │ ├── OneOfDiscount.java
│ │ │ └── OneOfDiscountValidator.java
│ │ └── rating
│ │ ├── OneOfRating.java
│ │ └── OneOfRatingValidator.java
│ └── resources
│ ├── Deal-kH.http
│ ├── application-dev.yaml
│ ├── application.yaml
│ ├── http-client.env.json
│ ├── static
│ └── templates
└── test
└── java
└── co
└── istad
└── dealkh
└── DealKhApiApplicationTests.java

75 directories, 182 files

```

### How to run the project

1. Clone the repository to your local machine.
2. Open a terminal and navigate to the project directory.
3. Run the following command to start the project:
4. Run the following command to start the project:

```
./gradlew bootRun
```

5. Open a web browser and navigate to `http://localhost:8080/api/v1/auth/login`.
6. Enter the following credentials:
    - Email: `layhak@gmail.com`
    - Password: `layhak`
7. Click on the "Login" button to authenticate.
8. You should now be able to access the API documentation at `http://localhost:8080/api/v1/docs`.

### Project dependencies

The project depends on the following libraries:

- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Lombok
- Swagger
- Gradle
- Gradle Wrapper
- PostgreSQL
- JUnit
- Mockito
- H2
- Hibernate
- Jackson
- MapStruct
- JPA
- Springdoc

### Project structure

The project is organized into the following directories and files:

- `src/main/java/co/istad/dealkh`: Contains the Java code for the project.
- `src/main/resources/application.yaml`: Contains the configuration for the project.

# Project Rest API Endpoints

The project provides the following REST API endpoints:

# Auth Controller Endpoints

The `AuthController` manages authentication and user registration by providing endpoints for login, token refresh, and
user registration. Below is a list of the endpoints available in the `AuthController`:

## Endpoints

### Login

- **URL:** `/api/v1/auth/login`
- **Method:** `POST`
- **Request Body:** `AuthRequest`
- **Response:** `BaseResponse<AuthResponse>`
- **Description:** Handles user login requests by providing an authentication response with access and refresh tokens.

### Refresh Token

- **URL:** `/api/v1/auth/refresh`
- **Method:** `POST`
- **Request Body:** `RefreshTokenRequest`
- **Response:** `BaseResponse<AuthResponse>`
- **Description:** Handles token refresh requests by providing new access and refresh tokens.

### Register User

- **URL:** `/api/v1/auth/register`
- **Method:** `POST`
- **Request Body:** `UserCreateRequest`
- **Response:** `BaseResponse<UserResponse>`
- **Response Status:** `201 Created`
- **Description:** Handles user registration requests by creating a new user and providing the created user response.

#### Example of `UserCreateRequest`:

```json
{
  "firstName": "Hom",
  "lastName": "Pheakakvotey",
  "username": "Votey",
  "email": "votey@gmail.com",
  "password": "votey",
  "gender": "gender",
  "phoneNumber": "0987654321",
  "dob": "2001-07-01",
  "location": "phnom penh",
  "roles": [
    "BUYER"
  ]
}
```

# Category Controller Endpoints

The `CategoryController` manages categories by providing endpoints for creating, retrieving, updating, and deleting
categories. Below is a list of the endpoints available in the `CategoryController`:

## Endpoints

### Create Category

- **URL:** `/api/v1/categories`
- **Method:** `POST`
- **Request Body:** `CategoryCreateRequest`
- **Response:** `BaseResponse<CategoryResponse>`
- **Description:** Creates a new category based on the provided request.

### Get Category by ID

- **URL:** `/api/v1/categories/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<CategoryResponse>`
- **Description:** Retrieves a category by its ID.

### Get Category by Name

- **URL:** `/api/v1/categories/{name}`
- **Method:** `GET`
- **Path Variable:** `name` (String)
- **Response:** `BaseResponse<Optional<CategoryResponse>>`
- **Description:** Retrieves a category by its name.

### Get All Categories

- **URL:** `/api/v1/categories`
- **Method:** `GET`
- **Response:** `BaseResponse<List<CategoryResponse>>`
- **Description:** Retrieves all categories.

### Update Category by Name

- **URL:** `/api/v1/categories/{name}`
- **Method:** `PUT`
- **Path Variable:** `name` (String)
- **Request Body:** `CategoryUpdateRequest`
- **Response:** `BaseResponse<CategoryResponse>`
- **Description:** Updates a category identified by its name based on the provided request.

### Delete Category by Name

- **URL:** `/api/v1/categories/{name}`
- **Method:** `DELETE`
- **Path Variable:** `name` (String)
- **Response:** `BaseResponse<?>`
- **Description:** Deletes a category identified by its name.

# Discount Controller Endpoints

The `DiscountController` manages discounts by providing endpoints for creating, retrieving, updating, and deleting
discounts. Below is a list of the endpoints available in the `DiscountController`:

## Endpoints

### Create Discount

- **URL:** `/api/v1/discounts`
- **Method:** `POST`
- **Request Body:** `DiscountCreateRequest`
- **Response:** `BaseResponse<DiscountTypeResponse>`
- **Description:** Creates a new discount based on the provided request.

### Get Discount by ID

- **URL:** `/api/v1/discounts/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<Optional<DiscountTypeResponse>>`
- **Description:** Retrieves a discount by its ID.

### Filter Discounts

- **URL:** `/api/v1/discounts`
- **Method:** `GET`
- **Query Parameters:**
    - `page` (default: 1) - The page number to retrieve.
    - `size` (default: 2) - The number of items per page.
    - `field` (default: id) - The field to sort by.
    - `order` (default: asc) - The order of sorting (asc or desc).
    - Additional filter parameters.
- **Response:** `PageResponse<DiscountTypeResponse>`
- **Description:** Retrieves a filtered list of discounts based on the provided query parameters.

### Update Discount by ID

- **URL:** `/api/v1/discounts/{id}`
- **Method:** `PUT`
- **Path Variable:** `id` (Long)
- **Request Body:** `DiscountUpdateRequest`
- **Response:** `BaseResponse<DiscountTypeResponse>`
- **Description:** Updates a discount identified by its ID based on the provided request.

### Delete Discount by ID

- **URL:** `/api/v1/discounts/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<?>`
- **Description:** Deletes a discount identified by its ID.

# Image Controller Endpoints

The `ImageController` manages images by providing endpoints for uploading, downloading, and deleting images. Below is a
list of the endpoints available in the `ImageController`:

## Endpoints

### Upload Single Image

- **URL:** `/api/v1/images`
- **Method:** `POST`
- **Consumes:** `multipart/form-data`
- **Request Part:** `file` (MultipartFile)
- **Response:** `BaseResponse<ImageUploadResponse>`
- **Response Status:** `201 Created`
- **Description:** Uploads a single image to the server.

### Upload Multiple Images

- **URL:** `/api/v1/images/multiple`
- **Method:** `POST`
- **Consumes:** `multipart/form-data`
- **Request Part:** `files` (List<MultipartFile>)
- **Response:** `BaseResponse<List<ImageUploadResponse>>`
- **Description:** Uploads multiple images to the server.

### Download Image

- **URL:** `/api/v1/images/download/{fileName}`
- **Method:** `GET`
- **Path Variable:** `fileName` (String)
- **Response:** `ResponseEntity<?>`
- **Description:** Serves an image from the server.

### Delete Image

- **URL:** `/api/v1/images/{fileName}`
- **Method:** `DELETE`
- **Path Variable:** `fileName` (String)
- **Response:** `BaseResponse<String>`
- **Description:** Deletes an image from the server.

# Order Controller Endpoints

The `OrderController` manages orders by providing endpoints for creating, retrieving, and deleting orders. Below is a
list of the endpoints available in the `OrderController`:

## Endpoints

### Get Orders by User ID

- **URL:** `/orders/{userId}`
- **Method:** `GET`
- **Path Variable:** `userId` (Long)
- **Response:** `BaseResponse<List<OrderResponse>>`
- **Operation Summary:** "Get all orders"
- **Description:** Retrieves all orders for a specific user based on the user ID.

### Create Order

- **URL:** `/orders`
- **Method:** `POST`
- **Request Body:** `OrderRequest`
- **Response:** `BaseResponse<OrderResponse>`
- **Operation Summary:** "Create order"
- **Description:** Creates a new order based on the provided request.

### Delete Order

- **URL:** `/orders/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Delete order"
- **Description:** Deletes an order based on the provided ID.

# Product Controller Endpoints

The `ProductController` manages products by providing endpoints for creating, retrieving, updating, and deleting
products. Below is a list of the endpoints available in the `ProductController`:

## Endpoints

### Create Product

- **URL:** `/api/v1/products`
- **Method:** `POST`
- **Request Body:** `ProductCreateRequest`
- **Response:** `BaseResponse<ProductResponse>`
- **Description:** Creates a new product based on the provided request.

### Get Product by ID

- **URL:** `/api/v1/products/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<Optional<ProductResponse>>`
- **Description:** Retrieves a product based on its ID.

### Filter Products

- **URL:** `/api/v1/products`
- **Method:** `GET`
- **Query Parameters:**
    - `page` (default: 1) - The page number to retrieve.
    - `size` (default: 2) - The number of items per page.
    - `field` (default: name) - The field to sort by.
    - `order` (default: asc) - The order of sorting (asc or desc).
    - Additional filter parameters.
- **Response:** `BaseResponse<PageResponse<ProductResponse>>`
- **Description:** Retrieves a filtered list of products based on the provided query parameters.

### Update Product by ID

- **URL:** `/api/v1/products/{id}`
- **Method:** `PUT`
- **Path Variable:** `id` (Long)
- **Request Body:** `ProductUpdateRequest`
- **Response:** `BaseResponse<ProductResponse>`
- **Description:** Updates a product based on its ID.

### Delete Product by ID

- **URL:** `/api/v1/products/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<?>`
- **Description:** Deletes a product based on its ID.

# Product Feedback Controller Endpoints

The `ProductFeedbackController` manages product feedback by providing endpoints for creating, retrieving, updating, and
deleting feedback. Below is a list of the endpoints available in the `ProductFeedbackController`:

## Endpoints

### Get Product Feedbacks by Product ID

- **URL:** `/api/v1/product-feedbacks/{productId}`
- **Method:** `GET`
- **Path Variable:** `productId` (Long)
- **Response:** `BaseResponse<List<ProductFeedbackResponse>>`
- **Operation Summary:** "Get product feedbacks by product id"
- **Description:** Retrieves feedback for a specific product based on the product ID.

### Create Product Feedback

- **URL:** `/api/v1/product-feedbacks`
- **Method:** `POST`
- **Request Body:** `ProductFeedbackRequest`
- **Response:** `BaseResponse<ProductFeedbackResponse>`
- **Operation Summary:** "Create product feedback"
- **Description:** Creates new feedback for a product based on the provided request.

### Update Product Feedback

- **URL:** `/api/v1/product-feedbacks/{id}`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Request Body:** `ProductFeedbackRequest`
- **Response:** `BaseResponse<ProductFeedbackResponse>`
- **Operation Summary:** "Update product feedback"
- **Description:** Updates feedback for a product based on its ID and the provided request.

### Delete Product Feedback

- **URL:** `/api/v1/product-feedbacks/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<?>`
- **Operation Summary:** "Delete product feedback"
- **Description:** Deletes feedback for a product based on its ID.

# Product Rating Controller Endpoints

The `ProductRatingController` manages product ratings by providing endpoints for creating and retrieving product
ratings. Below is a list of the endpoints available in the `ProductRatingController`:

## Endpoints

### Rate Product

- **URL:** `/api/v1/product-ratings`
- **Method:** `POST`
- **Request Body:** `ProductRatingRequest`
- **Response:** `ProductRatingResponse`
- **Description:** Rates a product based on the provided request.

### Get All Product Ratings

- **URL:** `/api/v1/product-ratings`
- **Method:** `GET`
- **Response:** `List<ProductRatingResponse>`
- **Description:** Retrieves all product ratings.

# Shop Controller Endpoints

The `ShopController` manages shops by providing endpoints for creating, retrieving, updating, and deleting shops. Below
is a list of the endpoints available in the `ShopController`:

## Endpoints

### Get All Shops

- **URL:** `/api/v1/shops`
- **Method:** `GET`
- **Query Parameters:**
    - `page` (default: 1) - The page number to retrieve.
    - `size` (default: 2) - The number of items per page.
    - `field` (default: name) - The field to sort by.
    - `order` (default: asc) - The order of sorting (asc or desc).
- **Response:** `BaseResponse<PageResponse<ShopResponse>>`
- **Operation Summary:** "Get all shops"
- **Description:** Retrieves a paginated list of all shops.

### Create Shop

- **URL:** `/api/v1/shops`
- **Method:** `POST`
- **Request Body:** `ShopRequest`
- **Response:** `BaseResponse<ShopResponse>`
- **Operation Summary:** "Create new shop"
- **Description:** Creates a new shop based on the provided request.

### Update Shop

- **URL:** `/api/v1/shops/{id}`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Request Body:** `ShopRequest`
- **Response:** `BaseResponse<ShopResponse>`
- **Operation Summary:** "Update shop"
- **Description:** Updates a shop identified by its ID based on the provided request.

### Get Shop by ID

- **URL:** `/api/v1/shops/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<ShopResponse>`
- **Operation Summary:** "Get shop by id"
- **Description:** Retrieves a shop identified by its ID.

### Delete Shop

- **URL:** `/api/v1/shops/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Delete shop"
- **Description:** Deletes a shop identified by its ID.

### Get Nearby Shops

- **URL:** `/api/v1/shops/nearby`
- **Method:** `GET`
- **Query Parameters:** `latitude` (double), `longitude` (double)
- **Response:** `BaseResponse<List<ShopResponse>>`
- **Operation Summary:** "Get nearby shops"
- **Description:** Retrieves a list of nearby shops based on the provided latitude and longitude.

### Get Shops by Shop Type

- **URL:** `/api/v1/shops/shop-type`
- **Method:** `GET`
- **Query Parameter:** `shopType` (String)
- **Response:** `BaseResponse<List<ShopResponse>>`
- **Operation Summary:** "Get shops by shop type"
- **Description:** Retrieves a list of shops based on the provided shop type.

### Disable Shop

- **URL:** `/api/v1/shops/{id}/disable`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<ShopResponse>`
- **Operation Summary:** "Disable shop"
- **Description:** Disables a shop identified by its ID.

### Enable Shop

- **URL:** `/api/v1/shops/{id}/enable`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<ShopResponse>`
- **Operation Summary:** "Enable shop"
- **Description:** Enables a shop identified by its ID.

### Get Shops by User ID

- **URL:** `/api/v1/shops/user/{userId}`
- **Method:** `GET`
- **Path Variable:** `userId` (Long)
- **Response:** `BaseResponse<List<ShopResponse>>`
- **Operation Summary:** "Get shops by user id"
- **Description:** Retrieves a list of shops associated with a specific user ID.

### Get Shops by Name

- **URL:** `/api/v1/shops/name`
- **Method:** `GET`
- **Query Parameter:** `name` (String)
- **Response:** `BaseResponse<List<ShopResponse>>`
- **Operation Summary:** "Get shops by name"
- **Description:** Retrieves a list of shops based on the provided name.

# Shop Type Controller Endpoints

The `ShopTypeController` manages shop types by providing endpoints for creating, retrieving, updating, and deleting shop
types. Below is a list of the endpoints available in the `ShopTypeController`:

## Endpoints

### Create Shop Type

- **URL:** `/api/v1/shop-types`
- **Method:** `POST`
- **Request Body:** `ShopTypeCreateRequest`
- **Response:** `BaseResponse<ShopTypeResponse>`
- **Description:** Creates a new shop type based on the provided request.

### Get Shop Type by Name

- **URL:** `/api/v1/shop-types/{name}`
- **Method:** `GET`
- **Path Variable:** `name` (String)
- **Response:** `BaseResponse<Optional<ShopTypeResponse>>`
- **Description:** Retrieves a shop type based on its name.

### Filter Shop Types

- **URL:** `/api/v1/shop-types`
- **Method:** `GET`
- **Query Parameters:** Various filter parameters.
- **Response:** `BaseResponse<PageResponse<ShopTypeResponse>>`
- **Description:** Retrieves a filtered list of shop types based on the provided query parameters.

### Update Shop Type by Name

- **URL:** `/api/v1/shop-types/{name}`
- **Method:** `PUT`
- **Path Variable:** `name` (String)
- **Request Body:** `ShopTypeUpdateRequest`
- **Response:** `BaseResponse<ShopTypeResponse>`
- **Description:** Updates a shop type identified by its name based on the provided request.

### Delete Shop Type by Name

- **URL:** `/api/v1/shop-types/{name}`
- **Method:** `DELETE`
- **Path Variable:** `name` (String)
- **Response:** `BaseResponse<?>`
- **Description:** Deletes a shop type identified by its name.

# User Controller Endpoints

The `UserController` manages users by providing endpoints for creating, retrieving, updating, and deleting users. Below
is a list of the endpoints available in the `UserController`:

## Endpoints

### Get All Users

- **URL:** `/api/v1/users`
- **Method:** `GET`
- **Query Parameters:**
    - `page` (default: 1) - The page number to retrieve.
    - `size` (default: 10) - The number of items per page.
    - `field` (default: username) - The field to sort by.
    - `order` (default: asc) - The order of sorting (asc or desc).
    - Additional filter parameters (default: {"status": "enable"}).
- **Response:** `BaseResponse<PageResponse<UserResponse>>`
- **Operation Summary:** "Get all users with pagination, sorting, and filtering"
- **Description:** Retrieves a paginated list of all users with sorting and filtering options.

### Get User by ID

- **URL:** `/api/v1/users/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<UserResponse>`
- **Description:** Retrieves a user by their ID.

### Delete User

- **URL:** `/api/v1/users/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Delete user"
- **Description:** Deletes a user by their ID.

### Update User

- **URL:** `/api/v1/users/{id}`
- **Method:** `PUT`
- **Path Variable:** `id` (Long)
- **Request Body:** `UserUpdateRequest`
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Update user"
- **Description:** Updates a user by their ID based on the provided request.

### Disable User

- **URL:** `/api/v1/users/{id}/disable`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Disable user"
- **Description:** Disables a user by their ID.

### Enable User

- **URL:** `/api/v1/users/{id}/enable`
- **Method:** `PATCH`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Enable user"
- **Description:** Enables a user by their ID.

### Get Current User Info

- **URL:** `/api/v1/users/me`
- **Method:** `GET`
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Get current user info"
- **Description:** Retrieves the current logged-in user's information.

### Get User Profile

- **URL:** `/api/v1/users/profile`
- **Method:** `GET`
- **Response:** `BaseResponse<UserProfileResponse>`
- **Operation Summary:** "Get user profile"
- **Description:** Retrieves the current logged-in user's profile information.

### Upload User Profile

- **URL:** `/api/v1/users/profile`
- **Method:** `POST`
- **Request Body:** `UserProfileRequest`
- **Response:** `BaseResponse<UserProfileResponse>`
- **Operation Summary:** "Upload user profile"
- **Description:** Updates the current logged-in user's profile.

### Delete User Profile Image

- **URL:** `/api/v1/users/profile/deleteImage`
- **Method:** `DELETE`
- **Query Parameter:** `imageUrl` (String)
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Delete user profile image"
- **Description:** Deletes the current logged-in user's profile image.

### Update User Password

- **URL:** `/api/v1/users/updatePassword`
- **Method:** `PUT`
- **Request Body:** `UserUpdatePasswordRequest`
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Update user password"
- **Description:** Updates the current logged-in user's password.

### Reset User Password

- **URL:** `/api/v1/users/resetPassword`
- **Method:** `PUT`
- **Request Body:** `UserResetPasswordRequest`
- **Response:** `BaseResponse<Void>`
- **Operation Summary:** "Reset user password"
- **Description:** Resets the current logged-in user's password.

### Add Role to User

- **URL:** `/api/v1/users/addRole`
- **Method:** `POST`
- **Request Body:** `UserRoleRequest`
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Add role to user"
- **Description:** Adds a role to the current logged-in user.

### Remove Role from User

- **URL:** `/api/v1/users/removeRole`
- **Method:** `DELETE`
- **Request Body:** `UserRoleRequest`
- **Response:** `BaseResponse<UserResponse>`
- **Operation Summary:** "Remove role from user"
- **Description:** Removes a role from the current logged-in user.

# Wish List Controller Endpoints

The `WishListController` manages wish lists by providing endpoints for creating, retrieving, updating, and deleting wish
lists. Below is a list of the endpoints available in the `WishListController`:

## Endpoints

### Create Wish List

- **URL:** `/api/v1/wishlists`
- **Method:** `POST`
- **Request Body:** `WishListRequest`
- **Response:** `BaseResponse<WishListResponse>`
- **Description:** Creates a new wish list based on the provided request.

### Get All Wish Lists

- **URL:** `/api/v1/wishlists`
- **Method:** `GET`
- **Query Parameters:** Various filter parameters.
- **Response:** `PageResponse<WishListResponse>`
- **Description:** Retrieves a filtered list of all wish lists based on the provided query parameters.

### Delete Wish List by ID

- **URL:** `/api/v1/wishlists/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<?>`
- **Description:** Deletes a wish list identified by its ID.

### Grant Wish List by ID

- **URL:** `/api/v1/wishlists/{id}/grant`
- **Method:** `POST`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<WishListResponse>`
- **Description:** Grants a wish list identified by its ID.

### Deny Wish List by ID

- **URL:** `/api/v1/wishlists/{id}/deny`
- **Method:** `POST`
- **Path Variable:** `id` (Long)
- **Response:** `BaseResponse<WishListResponse>`
- **Description:** Denies a wish list identified by its ID.
