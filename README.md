

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


To create banner.txt - https://devops.datenkollektiv.de/banner.txt/index.html
