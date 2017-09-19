package com.services.micro.data.bl.crud.impl.mongo;


import com.services.micro.data.api.response.ServiceResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDataRepository extends MongoRepository<ServiceResponse, String> {
}
