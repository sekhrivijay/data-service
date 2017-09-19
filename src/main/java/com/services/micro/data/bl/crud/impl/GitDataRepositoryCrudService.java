package com.services.micro.data.bl.crud.impl;


import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;

import javax.inject.Named;

@Named
public class GitDataRepositoryCrudService extends DataRepositoryCrudServiceBase {
    @Override
    public Data read(ServiceRequest serviceRequest) throws Exception {
        return new Data("This is a test ".getBytes());
    }

    @Override
    public Data create(ServiceRequest serviceRequest) throws Exception {
        return null;
    }
}
