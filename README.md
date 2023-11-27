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

[Pre-requisites](#pre-requisites) •
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

## Pre-requisites

#### Install following tools on your machine:

- [Git](https://git-scm.com)
- [Node.js](https://nodejs.org/en/) v18
- [Java](https://www.oracle.com/java/technologies/downloads/) v17
- [Docker](https://www.docker.com/products/docker-desktop/)

<hr />

#### Preferred IDE

- Frontend - [VS Code](https://code.visualstudio.com/download)
- Backend - [IntelliJ IDEA](https://www.jetbrains.com/edu-products/download/other-IIE.html)

<hr />

#### Cloning project

```bash
# Clone this repository
$ git clone https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group02.git
```

<hr />

## Build GrowBiz

1. In the backend directory, run `./mvnw install`.
2. After successful compilation and build, go to the root directory of the repository and run `docker-compose build`.
3. After docker images are built successfully, run `docker-compose up`.
4. `Ctrl + c` to stop the containers.
5. Please run `docker-compose down` after stopping the containers.

<hr />

## User Scenarios

<h3>Feature: Category Management</h3>

Scenario: Admin views all categories

    Given the admin is logged in
    When the admin requests to view all categories
    Then the admin should be able to see the list of existing categories
    And the list should include the category displaying its name and associated tax

Scenario: Add a new category by admin

    Given the admin is logged in
    When the admin adds a new category with name "Salon" and tax "10%"
    Then the category "Salon" with tax "10%" should be created successfully
    And the admin should be able to view the "Salon" category in the list of categories

Scenario: Update an existing category by admin

    Given the admin is logged in
    And there exists a category named "Salon" with tax "10%"
    When the admin updates the category "Salon" with new tax "7%"
    Then the category "Salon" should be updated successfully with tax "7%"
    And the admin should be able to view the updated tax for "Salon"

<h3>Feature: SubCategory Management</h3>

Scenario: Admin views subcategories under 'Salon' category

    Given the admin is logged in
    And there are existing subcategories under the "Salon" category
    When the admin clicks on the "Salon" category
    Then the admin should be able to view the list of subcategories
    And each subcategory should display its name and associated parent category "Salon"

Scenario: Admin adds a new subcategory under 'Salon' category

    Given the admin is logged in
    And the admin is viewing the "Salon" category
    When the admin adds a new subcategory with name "Hair Styling" under the "Salon" category
    Then the subcategory "Hair Styling" should be created successfully under the "Salon" category
    And the admin should be able to view the "Hair Styling" subcategory in the list of subcategories under "Salon"

Scenario: Admin updates a subcategory under 'Salon' category

    Given the admin is logged in
    And there exists a subcategory named "Hair Cut" under the "Salon" category
    When the admin updates the "Hair Cut" subcategory name to "Hair Color" under the "Salon" category
    Then the subcategory "Hair Cut" should be successfully updated to "Hair Color" under the "Salon" category
    And the admin should be able to view the updated "Hair Color" subcategory in the list of subcategories under "Salon"

<h3>Feature: Business Verification</h3>

Scenario: Admin views unverified businesses for verification

    Given the admin is logged in
    And there are unverified businesses pending for approval
    When the admin navigates to the verification section
    Then the admin should see the list of unverified businesses
    And each business should display its name, category, and link of documents

Scenario: Admin verifies a business

    Given the admin is logged in
    And there is an unverified business named "Best Beauty Salon"
    When the admin selects "Best Beauty Salon" for verification
    And the admin reviews the the downloaded documents uploaded by the business
    And finds the information satisfactory
    Then the admin approves "Best Beauty Salon" for verification
    And the status of "Best Beauty Salon" should be updated to 'Verified'

Scenario: Admin rejects a business

    Given the admin is logged in
    And there is an unverified business named "Nail Artistry"
    When the admin selects "Nail Artistry" for verification
    And the admin finds issues with the downloaded documents provided by the business
    And decides to reject "Nail Artistry" for verification with reason "Incomplete documents"
    Then the admin updates the status of "Nail Artistry" to 'Rejected'
    And provides the rejection reason as "Incomplete documents"

<h3>Feature: Operating Hours Management</h3>

Scenario: Partner views default operating hours

    Given the partner is logged in
    When the partner navigates to the Operating Hours section
    Then by default, Monday to Friday should be enabled
    And the default operating hours should be from 9 am to 5 pm for Monday to Friday
    And by default, Saturday and Sunday should be disabled

Scenario: Partner enables Saturday and Sunday

    Given the partner is logged in
    And the partner is in the Operating Hours section
    When the partner enables the operating hours for Saturday and Sunday
    Then Saturday and Sunday should be enabled for editing
    And the partner can set the operating hours for Saturday and Sunday as required

Scenario: Partner changes operating hours for specific days

    Given the partner is logged in
    And the partner is in the Operating Hours section
    When the partner selects a specific day, like Monday
    And chooses to modify the operating hours
    Then the partner should be able to change the opening and closing hours for Monday
    And the changes made should be saved successfully

<h3>Feature: Service Management</h3>

Scenario: Partner views existing services

    Given the partner is logged in
    When the partner navigates to the Services section
    Then the partner should see a list of existing services
    And each service should display its name, image, price per hour, time required, description, and a link to reviews

Scenario: Partner adds a new service

    Given the partner is logged in
    And the partner is in the Services section
    When the partner adds a new service named "Hair Cut" with price 30 CAD, time required 60 minutes, and adds a relevant image and description
    Then the service "Hair Cut" should be added successfully
    And the partner should be able to view the newly added "Hair Cut" service in the list of services

Scenario: Partner updates an existing service

    Given the partner is logged in
    And there exists a service named "Hair Cut" with price 30 CAD and time required 60 minutes
    When the partner updates the "Hair Cut" service to have a price of 35 CAD and changes the time required to 45 minutes
    Then the "Hair Cut" service should be updated successfully
    And the partner should be able to view the updated details for the "Hair Cut" service in the list of services

Scenario: Partner deletes a service

    Given the partner is logged in
    And there exists a service named "Hair Cut"
    When the partner decides to delete the "Hair Cut" service
    Then the "Hair Cut" service should be deleted successfully
    And the partner should not be able to view the "Hair Cut" service in the list of services

<h3>Feature: Upcoming Bookings</h3>

Scenario: Partner views upcoming bookings

    Given the partner is logged in
    When the partner navigates to the Upcoming Bookings section
    Then the partner should see a list of upcoming bookings
    And each booking should display service name, time required, date, start time, end time, customer email, amount, and any notes provided

Scenario: Partner sends a reminder to a customer for an upcoming booking

    Given the partner is logged in
    And there is an upcoming booking for a service
    When the partner selects the booking
    And chooses to send a reminder to the customer via email
    Then the partner should be able to send a reminder email to the customer for the upcoming booking
    And the email should contain relevant details about the booking

Scenario: Partner sets an upcoming booking as ongoing

    Given the partner is logged in
    And there is an upcoming booking for a service
    When the partner selects the upcoming booking
    And clicks on the "Set Ongoing" button
    Then the booking status should change from 'Upcoming' to 'Ongoing'
    And the booking should reflect as 'Ongoing' in the list of bookings

<h3>Feature: Ongoing Bookings</h3>

Scenario: Partner views ongoing bookings details

    Given the partner is logged in
    When the partner navigates to the Ongoing Bookings section
    Then the partner should see a list of ongoing bookings
    And each ongoing booking should display details including Service name, time required, date, start time, end time, customer email, amount, and note

Scenario: Partner updates ongoing booking status to completed

    Given the partner is logged in
    And there exists an ongoing booking for a specific service
    When the partner selects the ongoing booking for the service
    And updates the status from 'Ongoing' to 'Completed'
    Then the status of the ongoing booking should be successfully updated to 'Completed'
    And the partner should no longer see the booking in the ongoing bookings list

<h3>Feature: Past Bookings</h3>

Scenario: Partner views past bookings

    Given the partner is logged in
    When the partner navigates to the Past Bookings section
    Then the partner should see a list of past bookings
    And each booking should display details such as Service name, time required, date, start time, end time, customer email, and any additional notes

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