package com.services.micro.data.api.response;

import java.io.Serializable;

public class Debug implements Serializable {

    private long responseTime;

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "{" +
                "responseTime=" + responseTime +
                '}';
    }
}
