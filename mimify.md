# How to minify javascript and stylesheet

* Documentation : http://samaxes.github.io/minify-maven-plugin/examples/bundle.html
* Source : https://github.com/samaxes/minify-maven-plugin





## Method #1 해당 폴더의 js, css 파일들을 축소 하도록 설정하기

* minify-maven-plugin을 pom.xml에 추가

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


## Method #2 설정파일을 기반으로 파일들을 머지하고 축소화 하기

1. 설정파일 작성하기

>src/main/bundles/static-bundles.json

```json
{
    "bundles": [
        {
            "type": "css",
            "name": "global.css",
            "files": [
                "app/css/global-basic.css",
                "app/css/glyphicon.css"
            ]
        },
        {
            "type": "js",
            "name": "global.js",
            "files": [
                "app/js/global-init-menu.js"
            ]
        }
    ]
}
```

2. 메이븐 설정하기 

>pom.xml

```xml
<plugin>
    <groupId>com.samaxes.maven</groupId>
    <artifactId>minify-maven-plugin</artifactId>
    <version>1.7.6</version>
    <executions>
        <execution>
            <id>bundle-minify</id>
            <configuration>
                <charset>utf-8</charset>
                <bundleConfiguration>src/main/bundles/static-bundles.json</bundleConfiguration>
                <webappSourceDir>${basedir}/src/main/bundles</webappSourceDir>
                <webappTargetDir>${project.build.outputDirectory}/static</webappTargetDir>
                <jsSourceDir>/</jsSourceDir>
                <jsTargetDir>/js</jsTargetDir>
                <cssSourceDir>/</cssSourceDir>
                <cssTargetDir>/css</cssTargetDir>
            </configuration>
            <goals>
                <goal>minify</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

^^;