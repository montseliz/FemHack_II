# The International FemHack Vol.II Hackathon
## Backend FemHack Challenge


### 💬 Context

Cybersecurity is booming in today's market. Cases of cyber-attacks are on the rise. The main line of defence is in the area of application and website development. Within applications, one of the most vulnerable points revolves around user authentication and its associated components.

This challenge focuses on the implementation of a robust authentication system, addressing the essential considerations and incorporating basic user logging.

### ✍️ Technology stack

- Java 17
- Spring Boot 3.0.8
- Gradle-Kotlin
- Persistence in MongoDB
- JWT Authentication
- MFA with Jakarta Mail API » 2.0.1
- Open Api Documentation with Swagger 

The project has been developed using a technology stack that differs from the originally specified options of Python3, PHP, and JavaScript. The rationale behind this decision is to leverage the benefits and strengths offered by Java, Spring Boot, and related technologies, which are widely recognized and preferred in enterprise application development.

Java, with its robust ecosystem, provides strong static typing, excellent performance, and a vast array of libraries and frameworks. Spring Boot, a popular Java framework, simplifies the development process by offering a convention-over-configuration approach, built-in security features, and seamless integration with other Spring modules.

Gradle-Kotlin is chosen as the build tool, taking advantage of Kotlin's conciseness and expressiveness, which leads to more efficient and maintainable build scripts.

For data persistence, MongoDB has been selected as the database solution. MongoDB's flexibility, scalability, and document-oriented nature make it well-suited for handling complex data structures in modern applications.

To ensure secure authentication, the project incorporates JWT (JSON Web Token) authentication, which provides a stateless and secure mechanism for user verification.

Additionally, Multi-Factor Authentication (MFA) is implemented using the Jakarta Mail API. This enables an extra layer of security by requiring users to verify their identity through multiple channels.

OpenAPI documentation is generated with Swagger, facilitating clear and interactive API documentation that can be easily consumed by developers.

While the chosen technology stack deviates from the initially specified options, it enables us to deliver a robust, scalable, and secure application, aligning with industry best practices and providing an excellent user experience.

### 💻 Installation 

To get started with the project, follow the steps below:

1. Ensure that you have Java 17, Gradle and MongoDB installed on your system.

2. Clone the project repository to your local machine using the following command:

        git clone https://github.com/montseliz/FemHack_II.git
3. Navigate to the project directory:

        cd FemHack_II
4. Create the "femhack" database and the "users" collection in MongoDB.

5. Build the project using Gradle. Run the following command to compile and package the project:

        gradle build
6. Once the build is successful, you can run the application using the following command:

        gradle bootRun
7. The application will start running on "http://localhost:9005".

You can now access the endpoints and explore the functionality using tools like Swagger UI or your preferred API testing tool.

Please note that these are general installation instructions. Depending on your specific environment or requirements, you may need to adjust the steps accordingly.

### 🎯 Objectives

/*TODO*/

### 🔚 API Endpoints

**Register**

To register a user, send an HTTP POST request to the following URL:

        POST /api/auth/register

The request must include the following parameters:

- name: the user's name (string).
- email: the user's email (string).
- password: the user's password (string).

It will return the following responses:

- HTTP status 200 OK: 

![image.png](captures%2Fimage.png)

- HTTP status 409 CONFLICT: 

![image-1.png](captures%2Fimage-1.png)

**Login**

To authenticate a registered user with MFA, send an HTTP POST request to the following URL:

        POST /api/auth/home/login

The request must include the following parameters:

- email: the user's email (string).
- password: the user's password (string).

It will return the following responses:

- HTTP status 200 OK: 

![image-2.png](captures%2Fimage-2.png)

- HTTP status 401 UNAUTHORIZED: 

![image-3.png](captures%2Fimage-3.png)

**Verify**

To verify authentication of a registered user with MFA, send an HTTP POST request to the following URL: 

        POST /api/auth/home/verify

The request must include the following parameters:

- email: the user's email (string).
- verificationCode: 6 digit code sent to user's email address (string). 

It will return the following responses:

- HTTP status 200 OK: 

![image-4.png](captures%2Fimage-4.png)

- HTTP status 401 UNAUTHORIZED: 

![image-5.png](captures%2Fimage-5.png)

- HTTP status 400 BAD REQUEST: 

![image-6.png](captures%2Fimage-6.png)

**Log**

To get all connections made in the application. This endpoint is only accessible to an admin user. Send an HTTP GET request to the following URL: 

        GET /api/users/log

The request must include the following parameters:

- email: the admin user's email (string).
- password: the admin user's password (string).

It will return the following responses: 

- HTTP status 200 OK: 

![image-7.png](captures%2Fimage-7.png)

- HTTP status 403 FORBIDDEN: 

![image-8.png](captures%2Fimage-8.png)

- HTTP status 401 UNAUTHORIZED: 

![image-9.png](captures%2Fimage-9.png)

**Count**

To obtain the total number of users registered in the application. This enpoint has been implemented only to demonstrate that it can only be accessed with an authenticated user token. Send an HTTP GET request to the following URL: 

        GET /api/users/count

The request must include the bearer token as authorization, and it will return the following responses:

- HTTP status 200 OK: 

![image-10.png](captures%2Fimage-10.png)

- HTTP status 403 FORBIDDEN: 

![image-11.png](captures%2Fimage-11.png)

**Persistence with MongoDB**

![image-12.png](captures%2Fimage-12.png)

**API documentation with Swagger**

![image-13.png](captures%2Fimage-13.png)

### ➡️ Future steps

##### **Solution made by JavaFemCoders (Anna Santasusana & Montse Liz)**