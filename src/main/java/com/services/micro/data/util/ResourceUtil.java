package com.services.micro.data.util;


import com.services.micro.data.api.request.ServiceRequest;

import java.util.HashMap;
import java.util.Map;

import static com.services.micro.data.config.ConfigurationConstants.*;

public class ResourceUtil {



    public static String getFirstIfPresent(String[] inputList) {
        if (inputList == null || inputList.length == 0) {
            return null;
        }
        return inputList[0];
    }

    public static ServiceRequest buildServiceRequest(Map<String, String[]> queryParams) {
        Map<String, String> metaData = new HashMap<>();
        queryParams.entrySet()
                .stream()
                .filter(e -> !META_KEYS.contains(e.getKey()))
                .forEach(e -> metaData.put(e.getKey(), getFirstIfPresent(e.getValue())));

        return ServiceRequest.ServiceRequestBuilder.aServiceRequest()
                .withFileName(getFirstIfPresent(queryParams.get(FILE_NAME)))
                .withServiceName(getFirstIfPresent(queryParams.get(SERVICE_NAME)))
                .withEnvironment(getFirstIfPresent(queryParams.get(ENVIRONMENT)))
                .withMetaData(metaData)
                .build();


    }
}