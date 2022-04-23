WebSoket
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
```

```java
// config
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
// socket class
    @Component
    @ServerEndpoint("/msg/{userID}")
    public class MsgSocket {}
```

