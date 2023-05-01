## Scoring system

- This is a system which depicts overall flow how clients and deposits are scored/assessed in the banks. It is not a
  precise copy, but more of a blueprint which can be used as a plan.
- During creation, I simulated certain integrations which are usually done during the process.

### Overall architecture and tech stack

**Architecture**

- The system was done using microservices architecture as it enables each service to focus on the domain - DDD.
- Moreover, microservice architecture shines in the system when we want certain parts to fail fast, but certain
  to wait till the service will wake up. Also, it is much easier to deploy such services in containers if Docker
  is used, or pods in K8S

**Tech stack**

- **Java 17**. **Java** is used as it is the most common language for such systems due to compiled-time feature and 
  backward compatibility. 17 version is chosen as it is the latest LTS one. As usual, for minimizing boilerplate
  code I use `@Lombok` library
- For building the project I use **maven**. As in best practices, I have main `pom.xml` with parent dependencies
  and all child `pom`s either use something from parent `pom`s or use their own dependencies.
  - Moreover, I have services which are used as dependencies in other services `pom`s. It is done to either separate
    concerns or minimize code duplication as the presented code may be used in multiple services
  - Next, in services which are SpringBoot applications (not used as complimentary tools) there are 3 main plugins
    implemented:
    * `maven-compiler-plugin` for compiling code
    * `spring-boot-maven-plugin` for creating standalone application which is able to run on its own
    * `jib-maven-plugin` for creating and pushing docker images to the **docker registry**. In our case - **Docker Hub**.
- **SpringBoot**, **Spring Cloud** are used as main frameworks. Former is used for running application whilst latter 
  mainly for **Feign** and **ApiGateway** (this one is added as additional feature to see how Spring Cloud allows to create it)
- **RabbitMQ** and **Kafka** are used as brokers. Former is a queue type for decoupling __initial-checks__ service with 2 AI
    services: __applicant-models__ and __deposit-checks__. And later is for decoupling 2 AI services mentioned above
    with __final-checks__ service
- Each service, where it is required, has **PostgreSQL** database to store data in it. Database per service is done due to
  good practice where we don't allow other services to use another service database without communication through
  the owning service
- Continuing with databases: also I used hibernate, not the raw one, but **Spring Data JPA**. Traditional `@Entity` and
  `@Repository`
- **Observability** is a pretty important factor, hence I also implemented it: logs, metrics, tracing.
  - TODO: **logs** are to be stored in the database
  - **metrics** - SpringBoot actuator
  - **tracing** - Micrometer tracing using Brave. ZipKin for visualization

TODO: dead letter exchange in RabbitMQ