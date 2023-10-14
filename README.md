# GrowBiz

## Steps to Run the DB and Backend

1. In the backend directory, run `./mvnw install`.
2. After successful compilation and build, go to the root directory of the repository and run `docker-compose build`.
3. After docker images are built successfully, run `docker-compose up`.
4. Before testing the APIs, `roles` table has to be populated -
    1. Roles table can be populated using the following steps -
        1. `docker container exec -it mysql_db /bin/bash`
        2. `$ mysql -u root -p growbiz`
        3. Enter the password, which is test123.
        4. Run the following INSERT statements -
         ```sql
         -- Populate user roles table
         INSERT INTO roles(name) VALUES('ROLE_ADMIN');
         INSERT INTO roles(name) VALUES('ROLE_CUSTOMER');
         INSERT INTO roles(name) VALUES('ROLE_BUSINESS_OWNER');
         ```

5. API Table

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
</tr>
</thead>
<tbody>
<tr>
<td><code>/auth/signup</code></td>
<td><code>POST</code></td>
<td><code>ALL</code></td>
<td>{"firstName":"firstName","lastName":"lastName","email":"email","password":"password","role":"role"}</td>
<td>{"token":"token","subject":"email","role":"role"}</td>
<td><code>NO</code></td>
<td>This API is used to Sign Up a user.</td>
</tr>

<tr>
<td><code>/auth/authenticate</code></td>
<td><code>POST</code></td>
<td><code>ALL</code></td>
<td>{"email":"email","password":"password","role":"role"}</td>
<td>{"token":"token","subject":"email","role":"role"}</td>
<td><code>NO</code></td>
<td>This API is used to Login to the application.</td>
</tr>

<tr>
<td><code>/business/?email=email</code></td>
<td><code>GET</code></td>
<td><code>PARTNER</code></td>
<td></td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
<td><code>YES</code></td>
<td>This API is used to get the Business of the PARTNER.</td>
</tr>

<tr>
<td><code>/business/all</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code></td>
<td></td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
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
<td>file and business{"businessName":"businessName","email":"email","categoryId":"categoryId","role":"role"}</td>
<td>{"subject":"email","role":"role","businesses":[{"businessId":"businessId","businessName":"businessName","email":"email","status":"status","categoryId":"categoryId","fileURL":"fileURL"}]}</td>
<td><code>YES</code></td>
<td>This API is used to register a Business.</td>
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
<td></td>
<td>Verified!</td>
<td><code>YES</code></td>
<td>This API is used to update the businessStatus to APPROVED</td>
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

</tbody>
</table>

6. `Ctrl + c` to stop the containers.
7. Please run `docker-compose down` after stopping the containers.
