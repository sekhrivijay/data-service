package com.services.micro.data.api.response;

import java.io.Serializable;

public class Error implements Serializable{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static final class ErrorBuilder {
        private String message;

        private ErrorBuilder() {
        }

        public static ErrorBuilder anError() {
            return new ErrorBuilder();
        }

        public ErrorBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Error build() {
            Error error = new Error();
            error.setMessage(message);
            return error;
        }
    }
}
