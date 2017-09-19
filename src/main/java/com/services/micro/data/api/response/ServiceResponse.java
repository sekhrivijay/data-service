package com.services.micro.data.api.response;


public class ServiceResponse  {
    private Data data;
    private Status status;
    private int statusCode;
    private Error error;
    private Debug debug;



    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                ", data=" + data +
                ", status=" + status +
                ", statusCode=" + statusCode +
                ", error=" + error +
                ", debug=" + debug +
                '}';
    }

    public static final class ServiceResponseBuilder {
        private Data data;
        private Status status;
        private int statusCode;
        private Error error;
        private Debug debug;

        private ServiceResponseBuilder() {
        }

        public static ServiceResponseBuilder aServiceResponse() {
            return new ServiceResponseBuilder();
        }

        public ServiceResponseBuilder withData(Data data) {
            this.data = data;
            return this;
        }

        public ServiceResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ServiceResponseBuilder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ServiceResponseBuilder withError(Error error) {
            this.error = error;
            return this;
        }

        public ServiceResponseBuilder withDebug(Debug debug) {
            this.debug = debug;
            return this;
        }

        public ServiceResponse build() {
            ServiceResponse serviceResponse = new ServiceResponse();
            serviceResponse.setData(data);
            serviceResponse.setStatus(status);
            serviceResponse.setStatusCode(statusCode);
            serviceResponse.setError(error);
            serviceResponse.setDebug(debug);
            return serviceResponse;
        }
    }
}
