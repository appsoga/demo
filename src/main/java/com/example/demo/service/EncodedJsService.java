package com.example.demo.service;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import sangmok.util.aaEncode;

@lombok.Data
@Service
@ConfigurationProperties(prefix = "mysite")
public class EncodedJsService {

    @lombok.Data
    public static class Header {

        @JsonProperty(value = "content-type")
        private String contentType;

        @JsonProperty(value = "access_token")
        private String accessToken;

        @JsonProperty(value = "tranId")
        private String transId;

        @JsonProperty(value = "id")
        private String worker;

    }

    @lombok.Data
    public static class Environment {
        private String baseUrl;
        private Header headers;
    }

    private static final String APPLICATION_JSON = "application/json";

    private static Logger logger = LoggerFactory.getLogger(EncodedJsService.class);

    private Environment env;

    public Environment env() {

        if (env == null)
            env = new Environment();

        if (env.getBaseUrl() == null)
            env.setBaseUrl("");

        if (env.getHeaders() == null)
            env.setHeaders(new Header());

        Header header = env.getHeaders();
        if (header.getContentType() == null)
            header.setContentType(APPLICATION_JSON);
        header.setTransId(UUID.randomUUID().toString());

        // TODO: 여기서 값을 적용해야 하나?
        header.setWorker("appsoga");
        header.setAccessToken("accessToken");

        return env;
    }

    public String encodedJs() {
        StringBuilder sb = new StringBuilder();
        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(env());

            sb.append("(function (global) {");
            sb.append("var MyEnv = global.MyEnv || (global.MyEnv = ").append(json).append(");");
            sb.append("}(this));");
            logger.info(sb.toString());
            // return sb.toString();
            return new aaEncode(sb.toString()).get();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        sb.append("whow are you?");
        return sb.toString();

    }

}