<div align="center">

<div style="background-color: black; width: 200px; height: 60px; text-align: center; margin-top: 20px; margin-bottom: 5px">
  <a name="logo" href="http://os-vm163.research.cs.dal.ca:3000"><img src="https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fimage-removebg-preview.png?alt=media&token=014f8686-98df-4dfb-a1ac-1d0264bc6707" alt="GrowBiz" width="200"></a>
</div>

**Your trusted partner for on-demand home services, just a [click away](http://os-vm163.research.cs.dal.ca:3000)**
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

#### Admin credentials
Adin login URL: [login](http://os-vm163.research.cs.dal.ca:3000/admin/login)
Email: smit.patel@dal.ca
Password: Test@1234
Note: In user table update the role to ADMIN if you want to create new admin users

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

## Building and Locally Running GrowBiz

1. In the backend directory, run `./mvnw install`.
2. Create .env file in the frontend directory and put the following secrets in the file (Update the secrets as per your instance)
    ```
    NEXTAUTH_SECRET="password"
    NEXT_PUBLIC_SERVER_ADDRESS="http://localhost:9002"
    FRONTEND_NEXTAUTH_URL="http://localhost:3000"
    ```
3. After successful compilation and build, go to the root directory of the repository and run `docker-compose build`.
4. After docker images are built successfully, run `docker-compose up`.
5. Application can be accessed at `http://localhost:3000`.
6. `Ctrl + c` to stop the containers.
7. Please run `docker-compose down` after stopping the containers.

<hr />

## Deploying GrowBiz

While the deployment job is running in the GitLab pipeline, we essentially follow these steps:

1. Copy over the whole source code repository to the deployment virtual machine.
2. Run the below commands: 
   1. `docker compose build`.
   2. `docker compose up --detach`.
3. And the application is up and can be accessed at this [link](http://os-vm163.research.cs.dal.ca:3000).

## User Scenarios

### Admin Scenarios

#### Feature: Category Management

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

#### Feature: SubCategory Management

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

#### Feature: Business Verification

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

### Partner Scenarios
#### Feature: Partner Authentication

  Scenario: Partner logs in with valid credentials 
  ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FPartner%20login.png?alt=media&token=fb366f2e-25c6-4173-96f3-bfcdd63e3e1e)
 
    Given the partner is on the login page
    When the partner enters a valid username and password
    And clicks on the login button
    Then the system should authenticate the partner's credentials
    And the partner should be successfully logged in
  
  Scenario: Partner signs up by providing required details 
  ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FPartner%20signup.png?alt=media&token=74cef8b9-bd30-4acd-9453-f05185cdedb5)
 
    Given the partner is on the signup page
    When the partner enters their firstname "John", lastname "Doe", email "johndoe@example.com", password "SecurePwd123", and confirms the password
    And clicks on the signup button
    Then the system should register the partner's account
    And the partner should be successfully signed up
 
#### Feature: Admin Authentication
 
  Scenario: Admin logs in with valid credentials 
  ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FAdmin%20login.png?alt=media&token=7080c791-ad42-4ecd-877e-b43264fcc07c)
 
    Given the admin is on the login page
    When the admin enters a valid username and password
    And clicks on the login button
    Then the system should authenticate the admin's credentials
    And the admin should be successfully logged in

#### Feature: Business Registration
 
  Scenario: Partner registers a new business 
  ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FBusiness%20Registration.png?alt=media&token=5342afea-0478-44ad-af57-a74d06ff5576)
 
    Given the partner is logged in
    When the partner navigates to the Business Registration section
    And provides the business name as "Unique Salon"
    And selects a category for the business, like "Salon"
    And uploads the required verification documents
    Then the business "Unique Salon" should be registered successfully
    And the system should initiate the verification process for "Unique Salon"
 
#### Feature: Partner Views Business Verification Status
 
  Scenario: Partner checks business verification status as Pending 
  ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FVerification%20Pending.png?alt=media&token=065b849d-c3fd-4c5d-9a0d-353c787ed4f1)
    
    Given the partner is logged in
    When the partner navigates to the Business Verification Status section
    Then the partner should see the status of their business verification
    And if the business verification is pending
    Then the status displayed should indicate "Verification Pending"
 
  Scenario: Partner views rejection reason and updates business details ![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FVerification%20Failure.png?alt=media&token=62f8befb-1a53-40c2-9be0-d756872bb1a0)
    Given the partner is logged in
    And the partner's business was rejected by the admin with reason "Incomplete documents"
    When the partner has viewed the rejection reason
    And changes the business name to "New Name" and category to "New Category"
    And uploads new verification documents
    Then the partner should be able to submit the updated details for re-verification
    Given the partner has submitted updated details for re-verification
    Then the system should initiate the re-verification process for the partner's business with the updated information
#### Feature: Operating Hours Management


![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Foperating%20hours.png?alt=media&token=faccb896-3b55-46c7-95eb-4b5405c2c6e2)

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

#### Feature: Service Management

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

#### Feature: Upcoming Bookings

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

#### Feature: Ongoing Bookings


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

#### Feature: Past Bookings

Scenario: Partner views past bookings

    Given the partner is logged in
    When the partner navigates to the Past Bookings section
    Then the partner should see a list of past bookings
    And each booking should display details such as Service name, time required, date, start time, end time, customer email, and any additional notes

### Customer Scenarios
#### Feature: Authentication

Scenario: Successful customer login

    Given the customer is not logged in
    When the customer navigates to the Growbiz application
    Then the customer should see a login screen
    And upon entering the correct credentials and pressing login
    Then the customer should be redirected to the customer dashboard

Scenario: Failed customer login

    Given the customer is not logged in
    When the customer navigates to the Growbiz application
    Then the customer should see a login screen
    And upon entering the incorrect credentials and pressing login
    Then the customer should be see the login page again with invalid credentials error

#### Feature: Customer dashboard

Scenario: List all categories

    Given the customer is logged in
    When the customer navigates to the Growbiz dashboard (Root URL - host:3000)
    Then the customer should see a screen greeting them
    And the screen should a list of all the parent categories

Scenario: See partner services of a category

![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fservice%20listing.png?alt=media&token=ef830846-c05f-4014-b1b2-0498fb2025b0)

    Given the customer is logged in
    And the customer is on the dashbaord page
    When the customer clicks on a particular category card
    Then the application should should show a page listing all the subcategories of that category
    And under each sub category, the application should show all availables services of that subcategory created by the partners
    
Scenario: Filter partner services

    Given the customer is logged in
    And the customer is on the service listing screen
    When the customer types a string in the search bar
    Then the service is filtered based on the business name
    When the customer selects a unchecks a subcategory
    Then that subcategory section is hidden from the screen
    When the customer slides the price slider
    Then only the services whose price is lower than the selected price is visible
    When the customer slides the time slider
    Then only the services whose time requires is lower than the selected time is visible
    When the customer slides the rating slider
    Then only the services whose average ratings is above the selected rating is visible
  
#### Feature: Service Booking
Scenario: Open booking screen

    Given the customer is logged in
    And the cusomter is on the service listing screen
    When the customer clicks on any of the service
    Then see that the booking page of that service is visible

Scenario: See available slots based on business availability

    Given the customer is on the service booking screen
    When the customer clicks on the available date picker
    Then see that the future dates of the current weeek are enabled
    When the customer selects any particular date
    Then see that the availability dropdown shows all the free slots of that business based on their operating hours

Scenario: Successful service booking flow

    Given the customer is on the service booking screen
    When the customer clicks on the Book button
    Then see that the booking modal is open
    And the modal contains the selected date and time slots


#### Feature: Payment

![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Fpayment%20form.png?alt=media&token=e175cec7-831d-468e-803e-7a87c7d99828)

![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2FPayment%20details.png?alt=media&token=d4537e72-f40a-400a-bf1a-a67ebee824b7)
Scenario: Success payment flow

    Given the customer is on the booking flow modal
    When the customer clicks  proceed to payment button
    Then see that the payment form is visible to the customer
    When the customer add their valid payment details and clicks on Pay
    Then see that the customer is redirected to the payment screen
    And see the details of the payment
    When the payment is successful
    And customer goes to the booking page
    Then see that a successful service booking is made

Scenario: Failed payment flow

    Given the customer is on the booking flow modal
    When the customer clicks  proceed to payment button
    Then see that the payment form is visible to the customer
    When the customer add their invalid payment details and clicks on Pay
    Then see that the customer is show the error message on the modal

Scenario: See all payments

    Given the customer is logged in
    When the customer clicks on the `Profile` icon
    Then see the drodown open containing a menu with menu item "payments"
    When the customer clicks on the payments menu
    Then see that the customer is redirected to the page where all the payments initiated by the customer are visible


#### Feature: Bookings

Scenario: See all bookings

    Given the customer is logged in
    When the customer clicks on the `Bookings` icon
    Then see that the customer is redirected to the booking screen
    And the customer is able to see all the upcoming, ongoing and past bookings

Scenario: See updated booking status

    Given the customer is logged in
    When the business updates booking status
    And the customer refreshes the booking page
    Then see that the customer is show the updated booking status

#### Feature: Reviews
Scenario: See all service reviews

![](https://firebasestorage.googleapis.com/v0/b/growbiz-csci5308.appspot.com/o/staticImages%2Freviews.png?alt=media&token=ff5a3c92-8008-4484-b893-a10b0789614e)

    Given the customer is logged in
    When the customer is on the service booking page
    Then see that the customer is shown all the reviews of that service
    If the customer has not not added their review of the service
    Then see that a + icon is visible to the customer
    If the customer has added their review of the service
    Then see that the review is visible to the customer

Scenario: Add review succesfully

    Given the customer is on the service booking screen
    When the customer haven't a service review
    Then see that a + icon is visible to the customer
    When the customer clicks on the + icon
    Then the customer is show the add review form modal
    When the customer fills the review details
    And clicks on the "Save" button
    Then see that the added review is visible in the reviews section

Scenario: Update review sucessfully

    Given the customer is on the service booking screen
    When the customer has already added the service review
    Then see that their review is visible to the customer
    When the customer clicks on the pencil icon
    Then the customer is show the edit review form modal
    When the customer fills the review details
    And clicks on the "Save" button
    Then see that the updated review is visible in the reviews section


Scenario: Delete review sucessfully

    Given the customer is on the service booking screen
    When the customer has already added the service review
    Then see that their review is visible to the customer
    When the customer clicks on the Trash icon
    Then the customer is show a confirmation dialog
    When the customer clicks confirm
    Then see that the customer review is deleted
<hr />

## Dependency

#### Backend dependencies

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

#### Frontend dependencies

| Dependency                 | Version   | Usage                                       |
|----------------------------|-----------|---------------------------------------------|
| @hookform/resolvers        | ^3.3.2    | Form handling                                |
| @radix-ui/react-alert-dialog | ^1.0.5  | UI component - alert dialog                  |
| @radix-ui/react-aspect-ratio | ^1.0.3  | UI component - aspect ratio                  |
| @radix-ui/react-avatar     | ^1.0.4    | UI component - avatar                        |
| @radix-ui/react-checkbox   | ^1.0.4    | UI component - checkbox                      |
| @radix-ui/react-dialog     | ^1.0.5    | UI component - dialog                        |
| @radix-ui/react-dropdown-menu | ^2.0.6 | UI component - dropdown menu                 |
| @radix-ui/react-icons      | ^1.3.0    | UI component - icons                         |
| @radix-ui/react-label      | ^2.0.2    | UI component - label                         |
| @radix-ui/react-popover    | ^1.0.7    | UI component - popover                       |
| @radix-ui/react-progress   | ^1.0.3    | UI component - progress bar                  |
| @radix-ui/react-scroll-area | ^1.0.5  | UI component - scroll area                   |
| @radix-ui/react-select     | ^2.0.0    | UI component - select                        |
| @radix-ui/react-slider     | ^1.1.2    | UI component - slider                        |
| @radix-ui/react-slot       | ^1.0.2    | UI component - slot                          |
| @radix-ui/react-switch     | ^1.0.3    | UI component - switch                        |
| @radix-ui/react-toast      | ^1.1.5    | UI component - toast                         |
| @radix-ui/react-tooltip    | ^1.0.7    | UI component - tooltip                       |
| @stripe/react-stripe-js    | ^2.3.2    | Stripe integration                           |
| @stripe/stripe-js          | ^2.1.11   | Stripe integration                           |
| add                        | ^2.0.6    | Utility                                      |
| card                       | ^2.5.4    | UI                                      |
| class-variance-authority   | ^0.7.0    | Class utility                                      |
| clsx                       | ^2.0.0    | Utility                                      |
| date-fns                   | ^2.30.0   | Date manipulation                            |
| firebase                   | ^10.6.0   | Backend service integration (Firebase)       |
| lucide-react               | ^0.284.0  | Icon library                                 |
| moment                     | ^2.29.4   | Date manipulation                            |
| next                       | 13.5.4    | React framework                              |
| next-auth                  | ^4.23.2   | Authentication                               |
| react                      | ^18       | React framework                              |
| react-day-picker           | ^8.9.1    | Date picker                                  |
| react-dom                  | ^18       | React framework                              |
| react-hook-form            | ^7.47.0   | Form handling                                |
| react-modal                | ^3.16.1   | Modal component                              |
| shadcn-ui                  | ^0.4.1    | UI library                                   |
| tailwind-merge             | ^1.14.0   | Tailwind CSS utility                          |
| tailwindcss-animate        | ^1.0.7    | Tailwind CSS utility                          |
| zod                        | ^3.22.4   | Data validation                              |


<hr />

## Code Coverage

![Static Badge](https://img.shields.io/badge/JUnit_Test_Cases-186-blue)
![Static Badge](https://img.shields.io/badge/Class_Coverage-85%25-blue)
![Static Badge](https://img.shields.io/badge/Method_Coverage-76%25-blue)
![Static Badge](https://img.shields.io/badge/Line_Coverage-77%25-blue)

<hr />

## API Table

Go to the [backend readme](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group02/-/tree/main/backend) to see the API details

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