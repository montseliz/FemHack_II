# The International FemHack Vol.II Hackathon
## Backend FemHack Challenge


### ➡️ Context

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



### 🔚 API Endpoints

##### **Solution made by JavaFemCoders (Anna Santasusana & Montse Liz)**