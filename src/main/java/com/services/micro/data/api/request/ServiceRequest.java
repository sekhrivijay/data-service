package com.services.micro.data.api.request;

import com.services.micro.data.api.response.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServiceRequest implements Serializable {
    private String serviceName;
    private String fileName;
    private String environment;

    private Map<String, String> metaData = new HashMap<>();

    private int version;

    private Data data;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", environment='" + environment + '\'' +
                ", metaData=" + metaData +
                ", version=" + version +
                ", data=" + data +
                '}';
    }

    public static final class ServiceRequestBuilder {
        private String serviceName;
        private String fileName;
        private String environment;
        private Map<String, String> metaData = new HashMap<>();
        private int version;
        private Data data;

        private ServiceRequestBuilder() {
        }

        public static ServiceRequestBuilder aServiceRequest() {
            return new ServiceRequestBuilder();
        }

        public ServiceRequestBuilder withServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public ServiceRequestBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ServiceRequestBuilder withEnvironment(String environment) {
            this.environment = environment;
            return this;
        }

        public ServiceRequestBuilder withMetaData(Map<String, String> metaData) {
            this.metaData = metaData;
            return this;
        }

        public ServiceRequestBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public ServiceRequestBuilder withData(Data data) {
            this.data = data;
            return this;
        }

        public ServiceRequest build() {
            ServiceRequest serviceRequest = new ServiceRequest();
            serviceRequest.setServiceName(serviceName);
            serviceRequest.setFileName(fileName);
            serviceRequest.setEnvironment(environment);
            serviceRequest.setMetaData(metaData);
            serviceRequest.setVersion(version);
            serviceRequest.setData(data);
            return serviceRequest;
        }
    }
}
