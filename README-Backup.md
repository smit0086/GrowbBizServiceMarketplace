# GrowBiz

## Steps to Run the GrowBiz Application

1. In the backend directory, run `./mvnw install`.
2. After successful compilation and build, go to the root directory of the repository and run `docker-compose build`.
3. After docker images are built successfully, run `docker-compose up`.
4. API Table

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
<td>This API is used to Login to the application.</td>
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
<td><code>/admin/allCategories</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{[{"categoryID": id, "name": name, "tax": tax}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all business categories</td>
<td></td>
</tr>

<tr>
<td><code>/admin/get</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{{"categoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a business category by its ID</td>
<td></td>
</tr>

<tr>
<td><code>/admin/addCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"name": name, "tax": tax}</td>
<td>{{"categoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific business category</td>
<td></td>
</tr>

<tr>
<td><code>/admin/updateCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"categoryID": id, "name": name, "tax": tax}</td>
<td>{{"categoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific business category</td>
<td><ul><li>CategoryNotFoundException - 43102 - The specified category for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/deleteCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"categoryID": id, "name": name, "tax": tax}</td>
<td>{"isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific business category</td>
<td><ul><li>CategoryNotFoundException - 43102 - The specified category for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/allSubCategories</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{[{"subCategoryID": id, "name": name, "tax": tax}], "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all business subcategories</td>
<td></td>
</tr>

<tr>
<td><code>/admin/getSub</code></td>
<td><code>GET</code></td>
<td><code>ALL</code></td>
<td></td>
<td>{{"subCategoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a business subcategory by its ID</td>
<td></td>
</tr>

<tr>
<td><code>/admin/addSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"name": name, "tax": tax}</td>
<td>{{"subCategoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific business subcategory</td>
<td></td>
</tr>

<tr>
<td><code>/admin/updateSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"subCategoryID": id, "name": name, "tax": tax}</td>
<td>{{"subCategoryID": id, "name": name, "tax": tax}, "isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific business subcategory</td>
<td><ul><li>SubCategoryNotFoundException - 43202 - The specified category for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/deleteSubCategory</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"subCategoryID": id, "name": name, "tax": tax}</td>
<td>{"isSubCategory": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific business subcategory</td>
<td><ul><li>SubCategoryNotFoundException - 43202 - The specified subcategory for delete in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/allServices</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code><code>PARTNER</code></td>
<td></td>
<td>{[{"serviceId": serviceId, "serviceName": serviceName, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch all services</td>
<td></td>
</tr>

<tr>
<td><code>/admin/allServicesByBusinessId</code></td>
<td><code>GET</code></td>
<td><code>ADMIN</code><code>PARTNER</code></td>
<td>@RequestParam businessID=businessID</td>
<td>{[{"serviceId": serviceId, "serviceName": serviceName, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to fetch a specific a business service by its businessID</td>
<td></td>
</tr>

<tr>
<td><code>/admin/allServicesBySubCategoryId</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>@RequestParam subCategoryID=subCategoryID</td>
<td>{[{"serviceId": serviceId, "serviceName": serviceName,"price": price, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to add a specific a business service by its subCategoryID</td>
<td></td>
</tr>

<tr>
<td><code>/admin/updateService</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"serviceId": serviceId, "serviceName": serviceName, "price": price, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}</td>
<td>{[{"serviceId": serviceId, "serviceName": serviceName, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}], "isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to update a specific business service</td>
<td><ul><li>ServiceNotFoundException - 44002 - The specified business service for update in not found</li></ul></td>
</tr>

<tr>
<td><code>/admin/deleteService</code></td>
<td><code>POST</code></td>
<td><code>ADMIN</code></td>
<td>{"serviceId": serviceId, "serviceName": serviceName,"price": price, "description": description, "timeRequired": timeRequired, "businessId": "businessId", "subCategoryId": "subCategoryId"}</td>
<td>{"isUpdated": Boolean, "isDeleted": Boolean}</td>
<td><code>YES</code></td>
<td>This API is used to delete a specific business service</td>
<td><ul><li>ServiceNotFoundException - 44002 - The specified business service for delete in not found</li></ul></td>
</tr>

</tbody>
</table>

1. `Ctrl + c` to stop the containers.
2. Please run `docker-compose down` after stopping the containers.
