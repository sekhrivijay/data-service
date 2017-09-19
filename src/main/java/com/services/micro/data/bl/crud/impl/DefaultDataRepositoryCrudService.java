package com.services.micro.data.bl.crud.impl;


import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;
import com.services.micro.data.bl.crud.DataRepositoryCrudService;

import javax.inject.Named;

@Named
public class DefaultDataRepositoryCrudService implements DataRepositoryCrudService {
    @Override
    public Data read(ServiceRequest serviceRequest) throws Exception {
        return new Data();
    }

    @Override
    public Data create(ServiceRequest serviceRequest) throws Exception {
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
