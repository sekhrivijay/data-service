package com.services.micro.data.bl.crud.impl.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;
import com.services.micro.data.api.response.Status;
import com.services.micro.data.bl.crud.impl.DataRepositoryCrudServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.inject.Named;

import static com.services.micro.data.config.ConfigurationConstants.FILE_NAME;
import static com.services.micro.data.config.ConfigurationConstants.SERVICE_NAME;
import static com.services.micro.data.config.ConfigurationConstants.ENVIRONMENT;
import static com.services.micro.data.config.ConfigurationConstants.METADATA_DOT;


@Named
public class MongoDataRepositoryCrudService extends DataRepositoryCrudServiceBase {


    @Autowired
    private GridFsTemplate gridFsTemplate;


    @Override
    public Data read(ServiceRequest serviceRequest) throws Exception {
        checkIfValid(serviceRequest);
        GridFSDBFile gridFsdbFile = gridFsTemplate.findOne(new Query(buildCriteria(serviceRequest)));
        if (gridFsdbFile == null) {
            throw new Exception("File Not found in MongoDB");
        }
        return Data.DataBuilder.aData()
                .withInputStream(gridFsdbFile.getInputStream())
                .withStatus(Status.SUCCESSFUL)
                .build();

    }

    @Override
    public Data create(ServiceRequest serviceRequest) throws Exception {
        checkIfValid(serviceRequest);
        String id = gridFsTemplate.store(serviceRequest.getData().getInputStream(),
                serviceRequest.getFileName(),
                "bytes",
                buildDbObject(serviceRequest))
                .getId().toString();
        return Data.DataBuilder.aData()
                .withId(id)
                .withStatus(Status.SUCCESSFUL)
                .build();
    }


    @Override
    public Data delete(ServiceRequest serviceRequest) throws Exception {
        checkIfValid(serviceRequest);
        Query query = new Query(buildCriteria(serviceRequest));
        GridFSDBFile gridFsdbFile = gridFsTemplate.findOne(query);
        if (gridFsdbFile != null) {
            gridFsTemplate.delete(query);
        }
        return Data.DataBuilder.aData()
                .withStatus(Status.SUCCESSFUL)
                .build();
    }

    @Override
    public Data update(ServiceRequest serviceRequest) throws Exception {
        delete(serviceRequest);
        return create(serviceRequest);
    }


    private Criteria buildCriteria(ServiceRequest serviceRequest) {
        Criteria criteria = Criteria
                .where(METADATA_DOT + FILE_NAME)
                .is(serviceRequest.getFileName())
                .and(METADATA_DOT + SERVICE_NAME)
                .is(serviceRequest.getServiceName())
                .and(METADATA_DOT + ENVIRONMENT)
                .is(serviceRequest.getEnvironment());
        serviceRequest.getMetaData()
                .forEach((k, v) -> criteria.and(METADATA_DOT + k).is(v));
        return criteria;

    }

    private DBObject buildDbObject(ServiceRequest serviceRequest) {
        DBObject metaData = new BasicDBObject();
        metaData.put(ENVIRONMENT, serviceRequest.getEnvironment());
        metaData.put(SERVICE_NAME, serviceRequest.getServiceName());
        metaData.put(FILE_NAME, serviceRequest.getFileName());
        serviceRequest.getMetaData()
                .forEach(metaData::put);
        return metaData;
    }

}
