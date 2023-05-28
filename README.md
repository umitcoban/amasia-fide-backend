# amasia-fide-backend

# Application properties


## Database configuration

spring.datasource.url=jdbc:postgresql://localhost:5432/db_amasia_fide
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect
spring.datasource.platform=postgres
spring.jpa.hibernate.ddl-auto=none
spring.datasource.initialization-mode=always
spring.jpa.show-sql=true

# Spring configuration

spring.main.banner-mode=off
logging.level.org.springframework=ERROR

# Actuator Configuration
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# SMTP Configuration
spring.mail.host=mail.yourhost.com
spring.mail.port=465
spring.mail.username=yourmail.com
spring.mail.password=password
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.timeout=10000

# Quartz Configuration
spring.quartz.job-store-type=memory
spring.quartz.properties.org.quartz.threadPool.threadCount=10

# JWT Configuration
JWT.jwtCookieName=amasia-fide-auth
JWT.secret=ypIiBvRh8puqFluWS7kGvtqgCXmqxsdXTdimjIk42sI=
JWT.expiration=86400000
#Jacksn Configuration
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
