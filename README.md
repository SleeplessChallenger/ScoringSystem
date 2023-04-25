

1. Actually, there are multiple ways to send request to another microservice:
   * RestTemplate - we need to know port directly
   * Eureka - we don't need to know port directly and hence multiple instances on different ports run

2. Eureka:
   * Services register in Eureka server
   * Then another service looks up in Eureka server
   * If found - connect
   * So, Eureka server knows each client IP and port
   * If it is down - system can't work without it
     * So, same application (spring.application.name, but different ports) will be as single
       service with multiple instances

3. In SpringCloud if we want to send request and there can be multiple instances
   of our applications: use @LoadBalanced over i.e. RestTemplate @Bean
    ```
   final ResponseEntity<String> response = restTemplate.getForEntity(
                "http://INITIAL-CHECKS/api/v1/initialChecks/{applicantId}/{depositId}",
                String.class,
                applicantId, depositId
        );
   ```

4. On the picture with LB we have one external load balancer which will transmit traffic
   from the outer to the inner network. And also we have LB inside our network as we have
   multiple instances of the app.
   - Moreover, we have one LB with path-based routing. So, we redirect requests
     to the appropriate LB inside

5. In reality, it is always better to choose managed load balancer like GCP, ELB rather
   than configuring all by yourself (Spring Cloud Gateway)
6. Why use queues for AI/Scoring models? These services can be down, but requests can wait for
   some period in the queue (i.e. we can make picture on the front to notify applicants that
   it is currently working). Meanwhile, queue will keep those messages. So, queue will take our
   request and send response that it was accepted and now is being processed. After that we
   deal with our request, but previous part of the system doesn't wait => async
7. RabbitMQ doesn't allow you to store data in a cluster, whilst Kafka does
   - usually brokers are run in multiple availability zones (AZ)
8. RabbitMQ:
   - messages first land in _exchange_
   - _exchange_ forwards them based on the _routing pattern_ to the _binding_
   - _binding_: it binds _exchange_ to a particular queue
   - we can have many _exchanges_ and _queues_
   - exchange types:
     - direct: routing key == binding
     - fanout: message sent to every queue
     - topic: partial match
     - headers: uses message header instead of routing key
     - nameless: routing key == queue name
   - routing key and binding

9. If we don't end our check process due to some checks being activated, 
   we will give request with data to the queue
10. Observarbility:
    - Tracing in SpringBoot: https://www.appsdeveloperblog.com/micrometer-and-zipkin-in-spring-boot
    - https://github.com/openzipkin/zipkin/blob/master/zipkin-server/README.md#environment-variables
    - OpenTelemetry: https://github.com/open-telemetry/opentelemetry-specification/blob/main/specification/overview.md

11. Added profiles to Maven to make build & push of Docker container only with certain condition
12. Also, I use packaging - JAR

To create banner.txt - https://devops.datenkollektiv.de/banner.txt/index.html
