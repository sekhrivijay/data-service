package com.services.micro.data.bl;

import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.ServiceResponse;

public interface DataService {
    ServiceResponse create(ServiceRequest serviceRequest) throws Exception;
    ServiceResponse read(ServiceRequest serviceRequest) throws Exception;
    ServiceResponse update(ServiceRequest serviceRequest) throws Exception;
    ServiceResponse delete(ServiceRequest serviceRequest) throws Exception;
}
