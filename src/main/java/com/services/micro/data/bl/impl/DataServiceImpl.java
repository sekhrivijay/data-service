package com.services.micro.data.bl.impl;


import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;
import com.services.micro.data.bl.DataService;
import com.services.micro.data.api.response.ServiceResponse;
import com.services.micro.data.bl.crud.DataRepositoryServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named("dataService")
public class DataServiceImpl implements DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    @Inject
    private DataRepositoryServiceFactory dataRepositoryServiceFactory;

    @Timed
    @ExceptionMetered
//    @HystrixCommand(groupKey = "hystrixGroup",
// commandKey = "helloCommandKey",
// threadPoolKey = "helloThreadPoolKey",
// fallbackMethod = "fallbackHello")
    @Override
    public ServiceResponse read(ServiceRequest serviceRequest) throws Exception {
        return buildServiceResponse(serviceRequest,
                dataRepositoryServiceFactory.lookUp(serviceRequest).read(serviceRequest));
    }

    @Timed
    @ExceptionMetered
    @Override
    public ServiceResponse create(ServiceRequest serviceRequest) throws Exception {
        return buildServiceResponse(serviceRequest,
                dataRepositoryServiceFactory.lookUp(serviceRequest).create(serviceRequest));
    }


    @Timed
    @ExceptionMetered
    @Override
    public ServiceResponse update(ServiceRequest serviceRequest) throws Exception {
        return buildServiceResponse(serviceRequest,
                dataRepositoryServiceFactory.lookUp(serviceRequest).update(serviceRequest));

    }

    @Timed
    @ExceptionMetered
    @Override
    public ServiceResponse delete(ServiceRequest serviceRequest) throws Exception {
        return buildServiceResponse(serviceRequest,
                dataRepositoryServiceFactory.lookUp(serviceRequest).delete(serviceRequest));
    }

    private ServiceResponse buildServiceResponse(ServiceRequest serviceRequest, Data data) throws Exception {
        LOGGER.debug(serviceRequest.toString());
        return ServiceResponse.ServiceResponseBuilder.aServiceResponse()
                .withData(data)
//                .withMetaData(buildMetaData(serviceRequest))
                .build();
    }



}
