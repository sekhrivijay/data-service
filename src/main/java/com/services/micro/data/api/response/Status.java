package com.services.micro.data.api.response;

public enum Status {

    SUCCESSFUL("successful"),
    UNSUCCESSFUL("unsuccessful");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }

    public static Status getStatus(String status) {
        for (Status responseType : Status.values()) {
            if (responseType.getStatusString().equals(status)) {
                return responseType;
            }
        }
        return SUCCESSFUL;
    }
}
