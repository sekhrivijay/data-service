package com.services.micro.data.client;

import com.services.micro.data.config.DataServiceConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Map;
import org.springframework.http.MediaType;

import static com.services.micro.data.config.ConfigurationConstants.FILE_NAME;
import static com.services.micro.data.config.ConfigurationConstants.SERVICE_NAME;
import static com.services.micro.data.config.ConfigurationConstants.ENVIRONMENT;

@Component
@EnableConfigurationProperties(DataServiceConfigurationProperties.class)
public class DataServiceClient {

    @Value("${spring.profiles.active:local}")
    private String environment;

    @Value("${spring.application.name:DUMMY}")
    private String serviceName;

    @Value("${service.data.baseUrl:http://localhost:8080/ds/data/}")
    private String baseUrl;

    public void fetchFile(String fileNameRead, String fileNameWrite, Map<String, String> metaData) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(
                        MediaType.APPLICATION_OCTET_STREAM,
                        MediaType.ALL));

        ResponseExtractor<Void> responseExtractor = response -> {
            Files.copy(response.getBody(), Paths.get(fileNameWrite), StandardCopyOption.REPLACE_EXISTING);
            return null;
        };
        StringBuilder stringBuilder = new StringBuilder(baseUrl)
                .append("?")
                .append(getParam(SERVICE_NAME, serviceName))
                .append(getParam(FILE_NAME, fileNameRead))
                .append(getParam(ENVIRONMENT, environment));

        if (metaData != null) {
            metaData
                    .forEach((k, v) -> stringBuilder.append(getParam(k, v)));

        }
        restTemplate.execute(URI.create(stringBuilder.toString()), HttpMethod.GET, requestCallback, responseExtractor);
    }

    private String getParam(String key, String value) {
        return "&" + key + "=" + value;
    }

}
