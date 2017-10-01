package com.services.micro.data.bl.crud.impl;


import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;
import com.services.micro.data.bl.crud.DataRepositoryCrudService;

public abstract class DataRepositoryCrudServiceBase implements DataRepositoryCrudService {
//    protected Data fallbackData = new Data();


    protected void checkIfValid(ServiceRequest serviceRequest) throws Exception {
        if (serviceRequest == null
                || serviceRequest.getServiceName() == null
                || serviceRequest.getFileName() == null
                || serviceRequest.getEnvironment() == null) {
            throw new Exception("Invalid Request. serviceName or fileName or environment is missing ");
        }
    }

    @Override
    public Data create(ServiceRequest serviceRequest) throws Exception {
        return null;
    }

    @Override
    public Data read(ServiceRequest serviceRequest) throws Exception {
        return null;
    }

    @Override
    public Data update(ServiceRequest serviceRequest) throws Exception {
        return null;
    }

    @Override
    public Data delete(ServiceRequest serviceRequest) throws Exception {
        return null;
    }
}
