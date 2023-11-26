<div align="center">

<div style="background-color: black; width: 200px; height: 60px; text-align: center; margin-top: 20px; margin-bottom: 5px">
  <a name="logo" href="http://os-vm163.research.cs.dal.ca:3000"><img src="https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fimage-removebg-preview.png?alt=media&token=014f8686-98df-4dfb-a1ac-1d0264bc6707" alt="GrowBiz" width="200"></a>
</div>

**Your trusted partner for on-demand home services, just a click away**
<hr />

<p>
GrowBiz is a comprehensive platform that is designed to overcome the challenges faced by small-scale businesses, while
also introducing exciting new features to boost their growth and improve consumer’s experience.
</p>

[Build GrowBiz](#build-growbiz) •
[User Scenarios](#user-scenarios) •
[Dependency](#dependency) •
[Code Coverage](#code-coverage) •
[APIs](#api-table) •
[Contributors](#contributors)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Next JS](https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white)

<hr />

</div>

## Build GrowBiz

1. In the backend directory, run `./mvnw install`.
2. After successful compilation and build, go to the root directory of the repository and run `docker-compose build`.
3. After docker images are built successfully, run `docker-compose up`.
4. `Ctrl + c` to stop the containers.
5. Please run `docker-compose down` after stopping the containers.

<hr />

## User Scenarios

<hr />

## Dependency

<table>
<thead>
<tr>
<th>Dependency</th>
<th>Version</th>
<th>Usage</th>
<th>Link</th>
</tr>
</thead>
<tbody>
<tr>
<td>SpringBoot</td>
<td>3.1.4</td>
<td>Java Framework for REST APIs</td>
<td><a href="https://spring.io/projects/spring-boot">Link</a></td>
</tr>
<tr>
<td>Spring Security</td>
<td>3.1.4</td>
<td>Authentication and access-control framework</td>
<td><a href="https://spring.io/projects/spring-security">Link</a></td>
</tr>
<tr>
<td>Lombok</td>
<td>1.18.30</td>
<td>Autogenerate getter setters in POJO classes.</td>
<td><a href="https://projectlombok.org/">Link</a></td>
</tr>
<tr>
<td>MySQL Connector</td>
<td>8.1.0</td>
<td>Connects application to the MySQL</td>
<td><a href="https://mvnrepository.com/artifact/mysql/mysql-connector-java">Link</a></td>
</tr>
<tr>
<td>JSON WebToken</td>
<td>0.11.2</td>
<td>Generates JWTs in the application for authorization.</td>
<td><a href="https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt">Link</a></td>
</tr>
<tr>
<td>Stripe</td>
<td>24.2.0</td>
<td>Payment integration in the application.</td>
<td><a href="https://stripe.com/docs/api">Link</a></td>
</tr>

<tr>
<td>JUnit5</td>
<td>5.9.2</td>
<td>Unit testing of the modules.</td>
<td><a href="https://junit.org/junit5/docs/current/user-guide/">Link</a></td>
</tr>

<tr>
<td>Thymeleaf</td>
<td>3.1.4</td>
<td>Templating engine for HTML emails.</td>
<td><a href="https://www.thymeleaf.org/">Link</a></td>
</tr>

<tr>
<td>Jackson Datatype: JSR310</td>
<td>2.15.3</td>
<td>Add-on module to support JSR-310 (Java 8 Date & Time API) data types. </td>
<td><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310">Link</a></td>
</tr>
</tbody>
</table>

<hr />

## Code Coverage

![Static Badge](https://img.shields.io/badge/JUnit_Test_Cases-186-blue)
![Static Badge](https://img.shields.io/badge/Class_Coverage-85%25-blue)
![Static Badge](https://img.shields.io/badge/Method_Coverage-76%25-blue)
![Static Badge](https://img.shields.io/badge/Line_Coverage-77%25-blue)

<hr />

## API Table

<table>
<thead>
<tr>
<th>API</th>
<th>HTTP</th>
<th>Authority</th>
<th>Request</th>
<th>Response</th>
<th>Token</th>
<th>Usage</th>
<th>Exceptions</th>
</tr>
</thead>
<tbody>

<tr>
<td><code>/admin/addCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"name": "name", "tax": "tax"}</td>
<td>{"subject":"email","role":"role", "categories": [{"categoryID": "categoryID", "name": "name", "tax": "tax"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific business category</td>
<td></td>
</tr>

<tr>
<td><code>/admin/updateCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"categoryID": "categoryID", "name": "name", "tax": "tax"}</td>
<td>{"subject":"email","role":"role", "categories": [{"categoryID": "categoryID", "name": "name", "tax": "tax"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific business category</td>
<td><ul><li>CategoryNotFoundException - 43102 - The specified category for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/deleteCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"categoryID": "categoryID", "name": "name", "tax": "tax"}</td>
<td>{"isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific business category</td>
<td><ul><li>CategoryNotFoundException - 43102 - The specified category for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/addSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"name": "name", "categoryID": "categoryID"}</td>
<td>{"subject":"email","role":"role", "subCategories": [{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific business subcategory</td>
<td></td>
</tr>

<tr>
<td><code>/admin/updateSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}</td>
<td>{"subject":"email","role":"role", "subCategories": [{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific business subcategory</td>
<td><ul><li>SubCategoryNotFoundException - 43202 - The specified category for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/deleteSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}</td>
<td>{"subject":"email","role":"role","isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific business subcategory</td>
<td><ul><li>SubCategoryNotFoundException - 43202 - The specified subcategory for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/auth/signup</code></td>
<td><code>POST</code></td>
<td><code>ALL</code></td>
<td>{"firstName":"firstName","lastName":"lastName","email":"email","password":"password","role":"role"}</td>
<td>{"token":"token","subject":"email","role":"role"}</td>
<td><code>NO</code></td>
<td>This API is used to Sign Up a user.</td>
<td><ul><li>UserAlreadyExistsException - 41001 - The email you are trying to register is already registered</li></ul></td>
</tr>

<tr>
<td><code>/auth/authenticate</code></td>
<td><code>POST</code></td>
<td><code>ALL</code></td>
<td>{"email":"email","password":"password","role":"role"}</td>
<td>{"token":"token","subject":"email","role":"role"}</td>
<td><code>NO</code></td>
<td>This API is used to log in to the application.</td>
<td><ul><li>UsernameNotFoundException - 41002 - No Username exists with email: email</li><li>BadCredentialsException - 41003 - Incorrect username or password. Please sign in with correct credentials</li></ul></td>
</tr>

<tr>
<td><code>/business/?email=email</code></td>
<td><code>GET</code></td>
<td><code>PARTNER</code></td>
<td></td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL","description":"description"}]}</td>
<td><code>YES</code></td>
<td>This API is used to get the Business of the PARTNER.</td>
<td><ul><li>BusinessNotFoundException - 42002 - There is no business linked to the given email</li></ul></td>
</tr>

<tr>
<td><code>/business/all</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code></td>
<td></td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL","description":"description"}]}</td>
<td><code>YES</code></td>
<td>This API is used to get ALL the businesses in the Application.</td>
</tr>

<tr>
<td><code>/business/all?status=PENDING</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code></td>
<td></td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
<td><code>YES</code></td>
<td>This API is used to get ALL the businesses in the Application with Query param "status".</td>
</tr>

<tr>
<td><code>/business/save</code></td>
<td><code>POST</code></td>
<td><code>PARTNER</code></td>
<td>file and business{"businessName":"businessName","email":"email","categoryId":"categoryId","role":"role","description":"description"}</td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
<td><code>YES</code></td>
<td>This API is used to register a Business.</td>
<td><ul><li>BusinessAlreadyExistsException - 42001 - Business already exists with the given email</li></ul></td>
</tr>

<tr>
<td><code>/business/update/{businessId}</code></td>
<td><code>PUT</code></td>
<td><code>PARTNER</code></td>
<td>file and business{"businessName":"businessName","email":"email","categoryId":"categoryId","role":"role"}</td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
<td><code>YES</code></td>
<td>This API is used to update the business details based on businessId</td>
</tr>

<tr>
<td><code>/business/{businessId}/verify</code></td>
<td><code>PUT</code></td>
<td><code>ADMIN</code></td>
<td>{"status":"status", "reason":"reason"}</td>
<td>Business "businessName" has been APPROVED/DECLINED! Email has been sent to the Partner!</td>
<td><code>YES</code></td>
<td>This API is used to update the businessStatus to APPROVED/DECLINED. <i>(Note: An email is sent to the PARTNER)</i></td>
</tr>

<tr>
<td><code>/business/download?email=email</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code><code>PARTNER</code></td>
<td></td>
<td>MediaType.IMAGE_JPEG</td>
<td><code>YES</code></td>
<td>This API is used to download the verification document of the given PARTNER</td>
</tr>

<tr>
<td><code>/booking/add</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code>,<code>PARTNER</code>,<code>CUSTOMER</code></td>
<td></td>
<td></td>
<td><code>YES</code></td>
<td>This API is used to fetch all the bookings</td>
</tr>

<tr>
<td><code>/booking/user/{userId}</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code>,<code>PARTNER</code>,<code>CUSTOMER</code></td>
<td></td>
<td></td>
<td><code>YES</code></td>
<td>This API is used to fetch all the bookings for a user given user ID</td>
</tr>

<tr>
<td><code>/booking/service/{serviceId}</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code>,<code>PARTNER</code>,<code>CUSTOMER</code></td>
<td></td>
<td></td>
<td><code>YES</code></td>
<td>This API is used to fetch all the bookings for a service given service ID</td>
</tr>

<tr>
<td><code>/category/allCategories</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{"subject":"email","role":"role", "categories":[{"categoryID": id, "name": name, "tax": tax}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all business's categories</td>
<td></td>
</tr>

<tr>
<td><code>/category/getCategory</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td>@RequestParam Long categoryId=categoryId</td>
<td>{"subject":"email","role":"role", "categories":[{"categoryID": "categoryID", "name": "name", "tax": "tax"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a business's category by its ID</td>
<td></td>
</tr>

<tr>
<td><code>/category/allSubCategories</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{"subject":"email","role":"role", "subCategories":[{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all business subcategories</td>
<td></td>
</tr>

<tr>
<td><code>/category/getSubCategory</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td>@RequestParam Long subCategoryId=subCategoryId</td>
<td>{"subject":"email","role":"role", "subCategories":[{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a business subcategory by its ID</td>
<td></td>
</tr>

<tr>
<td><code>/category/getAllSubCategoriesForCategoryId</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td>@RequestParam Long categoryId=categoryId</td>
<td>{"subject":"email","role":"role", "subCategories":[{"subCategoryID": "subCategoryID", "name": "name", "categoryID": "categoryID"}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all business subcategories based on its parent category ID</td>
<td></td>
</tr>

<tr>
<td><code>/reviews/allReviewsAndRatings</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{"subject":"email","role":"role","reviewsAndRatings": [{"reviewAndRatingId": reviewAndRatingId, "review": review, "rating": rating, "userId": userId, "serviceId": serviceId, "userEmail": userEmail}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all reviews and ratings</td>
<td></td>
</tr>

<tr>
<td><code>/reviews/allReviewsAndRatingsByServiceId</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td>@RequestParam Long serviceID=serviceID</td>
<td>{"subject":"email","role":"role","reviewsAndRatings": [{"reviewAndRatingId": "reviewAndRatingId", "review": "review", "rating": "rating", "userId": "userId", "serviceId": "serviceId", "userEmail": "userEmail"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all reviews based on its parent service ID</td>
<td></td>
</tr>

<tr>
<td><code>/reviews/addReviewAndRating</code></td>
<td><code>POST</code></td>
<td><code>CUSTOMER</code></td>
<td>{"review": "review", "rating": rating, "userId": userId, "serviceId": serviceId, "userEmail": userEmail}}</td>
<td>{"subject":"email","role":"role","reviewsAndRatings": [{"reviewAndRatingId": "reviewAndRatingId", "review": "review", "rating": "rating", "userId": "userId", "serviceId": "serviceId", "userEmail": "userEmail"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add user's review for a specific service</td>
<td><ul><li>ReviewAndRatingAlreadyExists - 45001 - The requested new review and rating to add, already exists</li></ul></td>
</tr>

<tr>
<td><code>/reviews/updateReviewAndRating</code></td>
<td><code>POST</code></td>
<td><code>CUSTOMER</code></td>
<td>{"reviewAndRatingId": "reviewAndRatingId", "review": "review", "rating": "rating", "userId": "userId", "serviceId": "serviceId", "userEmail": "userEmail"}</td>
<td>{"subject":"email","role":"role","reviewsAndRatings": [{"reviewAndRatingId": "reviewAndRatingId", "review": "review", "rating": "rating", "userId": "userId", "serviceId": "serviceId", "userEmail": "userEmail"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a user's review for a specific service</td>
<td><ul><li>ReviewAndRatingNotFoundException - 45002 - The specified review and rating for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/reviews/deleteReviewAndRating</code></td>
<td><code>POST</code></td>
<td><code>CUSTOMER</code></td>
<td>@RequestParam Long reviewAndRatingId=reviewAndRatingId</td>
<td>{"isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete user's review for a specific service</td>
<td><ul><li>ReviewAndRatingNotFoundException - 45002 - The specified review and rating for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/services/allServices</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code><code>PARTNER</code></td>
<td></td>
<td>{"subject":"email","role":"role","services": [{"serviceId": serviceId, "serviceName": serviceName, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all services</td>
<td></td>
</tr>

<tr>
<td><code>/services/allServicesByBusinessId</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code><code>PARTNER</code></td>
<td>@RequestParam businessID=businessID</td>
<td>{"subject":"email","role":"role","services": [{"serviceId": "serviceId", "serviceName": "serviceName", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a service by its business ID</td>
<td></td>
</tr>

<tr>
<td><code>/services/allServicesBySubCategoryId</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>@RequestParam subCategoryID=subCategoryID</td>
<td>{"subject":"email","role":"role","services": [{"serviceId": "serviceId", "serviceName": "serviceName","price": "price", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific a service by its subCategory ID</td>
<td></td>
</tr>

<tr>
<td><code>/services/allServicesByCategoryId</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>@RequestParam categoryID=categoryID</td>
<td>{"subject":"email","role":"role","serviceDTO":[{"serviceId": "serviceId", "serviceName": serviceName,"price": "price", "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId", "imageURL": "imageURL"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific a service by the parent category ID</td>
<td></td>
</tr>

<tr>
<td><code>/services/addService</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"serviceId": "serviceId", "serviceName": "serviceName", "price": "price", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId"}</td>
<td>{"subject":"email","role":"role","services": [{"serviceId": "serviceId", "serviceName": "serviceName", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId","imageURL": "imageURL"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific service</td>
<td><ul><li>ServiceAlreadyExistsException - 44001 - The requested new service to add, already exists</li></ul></td>
</tr>

<tr>
<td><code>/services/updateService</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"serviceId": "serviceId", "serviceName": "serviceName", "price": "price", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId"}</td>
<td>{"subject":"email","role":"role","services": [{"serviceId": "serviceId", "serviceName": "serviceName", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId", "imageURL": "imageURL"}], "isUpdated": Boolean, "isDeleted": Boolean, "avgRatings": [Double]}}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific service</td>
<td><ul><li>ServiceNotFoundException - 44002 - The specified business service for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/services/deleteService</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"serviceId": "serviceId", "serviceName": "serviceName","price": "price", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId", "imageURL": "imageURL"}</td>
<td>{"isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific service</td>
<td><ul><li>ServiceNotFoundException - 44002 - The specified business service for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/services/getService</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code></td>
<td>@RequestParam Long serviceId=serviceID</td>
<td>{"subject":"email","role":"role","services": [{"serviceId": "serviceId", "serviceName": "serviceName", "description": "description", "timeRequired": "timeRequired", "businessId": "businessId", "subCategoryId": "subCategoryId", "imageURL": "imageURL"}], "tax": String, "avgRatings": [Double]}</td>
<td><code>YES</code></td>
<td>This API is used to get a specific service, along with tax and average rating </td>
<td><ul><li>ServiceNotFoundException - 44002 - The specified business service for delete in not found</li></ul></td>
</tr>

</tbody>
</table>

<hr />

## Contributors

* Aniket
  Mhatre [![Aniket Mhatre](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2F317750_linkedin_icon%20(1).png?alt=media&token=8faf3dbc-5959-42ce-b1c8-7b2b348a09c2)](https://www.linkedin.com/in/maniket/) [![Aniket Mhatre](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fcxv0BGAX3aaAVraSAAAZlVgxMZg056-r.png?alt=media&token=ce87ce75-bb82-4080-9537-91b8769442b6)](https://github.com/aniketm07)
* Smit
  Patel [![Smit Patel](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2F317750_linkedin_icon%20(1).png?alt=media&token=8faf3dbc-5959-42ce-b1c8-7b2b348a09c2)](https://www.linkedin.com/in/smit0086/) [![Smit Patel](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fcxv0BGAX3aaAVraSAAAZlVgxMZg056-r.png?alt=media&token=ce87ce75-bb82-4080-9537-91b8769442b6)](https://github.com/smit0086)
* Rabia
  Asif [![Rabia_Asif](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2F317750_linkedin_icon%20(1).png?alt=media&token=8faf3dbc-5959-42ce-b1c8-7b2b348a09c2)](https://www.linkedin.com/in/rabia-asif-a95b43188/) [![Rabia Asif](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fcxv0BGAX3aaAVraSAAAZlVgxMZg056-r.png?alt=media&token=ce87ce75-bb82-4080-9537-91b8769442b6)](https://github.com/travis068)
* Vivek
  Sonani [![Vivek_Sonani](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2F317750_linkedin_icon%20(1).png?alt=media&token=8faf3dbc-5959-42ce-b1c8-7b2b348a09c2)](https://www.linkedin.com/in/vivek-sonani-75323b215/) [![Vivek Sonani](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fcxv0BGAX3aaAVraSAAAZlVgxMZg056-r.png?alt=media&token=ce87ce75-bb82-4080-9537-91b8769442b6)](https://github.com/Vivek504)
* Prithvi Manoj
  Krishna [![Prithvi_Krishna](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2F317750_linkedin_icon%20(1).png?alt=media&token=8faf3dbc-5959-42ce-b1c8-7b2b348a09c2)](https://www.linkedin.com/in/pmk21/) [![Prithvi Krishna](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fcxv0BGAX3aaAVraSAAAZlVgxMZg056-r.png?alt=media&token=ce87ce75-bb82-4080-9537-91b8769442b6)](https://github.com/pmk21/)

<hr />