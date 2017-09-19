package com.services.micro.data.bl.crud;


import com.services.micro.data.api.request.ServiceRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class DataRepositoryServiceFactory {
    private static Logger logger = Logger.getLogger(DataRepositoryServiceFactory.class.getName());

    @Inject
    @Named("mongoDataRepositoryCrudService")
    private DataRepositoryCrudService mongoDataRepositoryCrudService;

    @Inject
    @Named("ftpDataRepositoryCrudService")
    private DataRepositoryCrudService ftpDataRepositoryCrudService;

    @Inject
    @Named("gitDataRepositoryCrudService")
    private DataRepositoryCrudService gitDataRepositoryCrudService;

    @Inject
    @Named("minioDataRepositoryCrudService")
    private DataRepositoryCrudService minioDataRepositoryCrudService;

    @Inject
    @Named("s3DataRepositoryCrudService")
    private DataRepositoryCrudService s3DataRepositoryCrudService;

    @Inject
    @Named("defaultDataRepositoryCrudService")
    private DataRepositoryCrudService defaultDataRepositoryCrudService;


    @Inject
    private ServiceRepositorySources serviceRepositorySources;


    public DataRepositoryCrudService lookUp(ServiceRequest serviceRequest) {
        logger.debug(serviceRepositorySources);
        logger.info(serviceRequest);
        DataRepositoryCrudService dataCrudService = mongoDataRepositoryCrudService;
        if (serviceRequest == null || StringUtils.isEmpty(serviceRequest.getFileName()) || StringUtils.isEmpty(serviceRequest.getServiceName())) {
            return dataCrudService;
        }
        Map<String, String> metaKey = new HashMap<>();
        metaKey.put("serviceName", serviceRequest.getServiceName());
        metaKey.put("fileName", serviceRequest.getFileName());
        if (serviceRepositorySources.getGit().contains(metaKey)) {
            return gitDataRepositoryCrudService;
        }

        if (serviceRepositorySources.getMongo().contains(metaKey)) {
            return mongoDataRepositoryCrudService;
        }


        return dataCrudService;
    }


}
