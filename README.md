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
5. APIs are accessible on `localhost:8080`.
   1. Curl command to populate the APIs - 
   ```bash
   $ curl http://localhost:8080/api/add -d name=Second -d email=someemail@someemailprovider.com -d password=test123 -d role=admin
   ```
6. `Ctrl + c` to stop the containers.
7. Please run `docker-compose down` after stopping the containers.
