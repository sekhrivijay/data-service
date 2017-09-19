package com.services.micro.data.api.response;

import java.io.InputStream;
import java.io.Serializable;

public class Data implements Serializable {

    public Data(byte[] rawData) {
        this.rawData = rawData;
    }

    public Data() {
    }

    private Status status;

    private String id;

    private byte[] rawData;

    private long length ;

    private InputStream inputStream;

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public static final class DataBuilder {
        private Status status;
        private String id;
        private byte[] rawData;
        private long length ;
        private InputStream inputStream;

        private DataBuilder() {
        }

        public static DataBuilder aData() {
            return new DataBuilder();
        }

        public DataBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public DataBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public DataBuilder withRawData(byte[] rawData) {
            this.rawData = rawData;
            return this;
        }

        public DataBuilder withLength(long length) {
            this.length = length;
            return this;
        }

        public DataBuilder withInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Data build() {
            Data data = new Data();
            data.setStatus(status);
            data.setId(id);
            data.setRawData(rawData);
            data.setLength(length);
            data.setInputStream(inputStream);
            return data;
        }
    }
}
