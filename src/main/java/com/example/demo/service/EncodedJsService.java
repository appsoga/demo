package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import sangmok.util.aaEncode;

@Service
@ConfigurationProperties(prefix = "site.encoded.js")
public class EncodedJsService {

    @lombok.Data
    public static class Environment {
        private String webapiUrl;
        private String token;
        private String worker;
    }

    private static Logger logger = LoggerFactory.getLogger(EncodedJsService.class);

    @lombok.Getter
    @lombok.Setter
    private String webapiUrl;

    @lombok.Getter
    @lombok.Setter
    private String token;

    public Environment env() {
        Environment env = new Environment();
        env.setWebapiUrl(webapiUrl);
        env.setToken(token);
        return env;
    }

// (function (global) {
//     var Demo = global.Demo || (global.Demo = {});
//     Demo.webapiUrl = "http://localhost:8080";
// }(this));

    public String encodedJs() {
        StringBuilder sb = new StringBuilder();
        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(env());

            sb.append("(function (global) {")
                .append("var Demo = global.Demo || (global.Demo = ").append(json).append(");")
                .append("Demo.url = \"").append("http://localhost:8080").append("\";")
                .append("}(this));");
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