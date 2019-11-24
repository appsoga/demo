# demo

example app with Spring Boot 2.2.0

## Packages

* Spring Boot 2.2.0
* thymeleaf
* devtools
* lombok
* jpa
* h2


## Include webjars

* jquery 2.2.4
* jquery-ui 1.12.1
* bootstrap 4.3.1

## Execute

```bash
mvn spring-boot:run
```

## Add options to pom.xml

```xml
<dependency>
  <groupId>nz.net.ultraq.thymeleaf</groupId>
  <artifactId>thymeleaf-layout-dialect</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>jquery</artifactId>
  <version>2.2.4</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>bootstrap</artifactId>
  <version>4.3.1</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>datatables</artifactId>
  <version>1.10.20</version>
</dependency>
```
