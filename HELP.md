# Read Me First
The following was discovered as part of building this project:

- install java 11
- mvn clean install

# Getting Started

- in-memory database console => http://localhost:8091/h2-console
- request for all wishs => curl --location 'http://localhost:8091/wishs'
- request for find wish by id => curl --location 'http://localhost:8090/wishs/{id}'

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)

