# demo2
demo app with spring-boot 2

## Packages
* Spring Boot 2.2.0
* thymeleaf
* devtools
* lombok
* jpa
* h2


## Webjars
* jquery 3.3.1
* bootstrap 4.0.0
* datatables 1.10.20

## 실행하기
```bash
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8787 -Dserver.port=9090 -Dpath.to.config.dir=/var/data/my/config/dir"
```

## 기본환경
```xml
<dependency>
  <groupId>nz.net.ultraq.thymeleaf</groupId>
  <artifactId>thymeleaf-layout-dialect</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>jquery</artifactId>
  <version>3.3.1</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>bootstrap</artifactId>
  <version>4.0.0</version>
</dependency>
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>datatables</artifactId>
  <version>1.10.20</version>
</dependency>
```

## css, js minify 처리하기 위해서.

* http://samaxes.github.io/minify-maven-plugin/examples/bundle.html
* Source: https://github.com/samaxes/minify-maven-plugin

```xml
<plugin>
  <groupId>com.samaxes.maven</groupId>
  <artifactId>minify-maven-plugin</artifactId>
  <version>1.7.6</version>
  <executions>
    <execution>
      <id>minify</id>
      <phase>process-resources</phase>
      <goals>
        <goal>minify</goal>
      </goals>
      <configuration>
        <charset>utf-8</charset>
        <skipMerge>true</skipMerge>
        <nosuffix>true</nosuffix>
        <jsEngine>CLOSURE</jsEngine>
        <webappSourceDir>${basedir}/src/main/resources/static</webappSourceDir>
        <webappTargetDir>${project.build.outputDirectory}/static</webappTargetDir>

        <jsSourceDir>/js</jsSourceDir>
        <jsTargetDir>/js</jsTargetDir>
        <jsSourceIncludes>
          <jsSourceInclude>**/*.js</jsSourceInclude>
        </jsSourceIncludes>
        <jsSourceExcludes>
          <jsSourceExclude>**/*.min.js</jsSourceExclude>
        </jsSourceExcludes>

        <cssSourceDir>/css</cssSourceDir>
        <cssTargetDir>/css</cssTargetDir>
        <cssSourceIncludes>
          <cssSourceInclude>**/*.css</cssSourceInclude>
        </cssSourceIncludes>
        <cssSourceExcludes>
          <cssSourceExclude>**/*.min.css</cssSourceExclude>
        </cssSourceExcludes>

      </configuration>
    </execution>
  </executions>
</plugin>
```