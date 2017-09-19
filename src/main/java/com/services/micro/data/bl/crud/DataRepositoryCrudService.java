package com.services.micro.data.bl.crud;

import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;

public interface DataRepositoryCrudService  {
    Data create(ServiceRequest serviceRequest) throws Exception;
    Data read(ServiceRequest serviceRequest) throws Exception;
    Data update(ServiceRequest serviceRequest) throws Exception;
    Data delete(ServiceRequest serviceRequest) throws Exception;
}
