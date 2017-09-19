package com.services.micro.data.bl.crud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "service.sources")
public class ServiceRepositorySources {


    private List<Map<String, String>> git = new ArrayList<>();
    private List<Map<String, String>> mongo = new ArrayList<>();

    public List<Map<String, String>> getGit() {
        return git;
    }

    public void setGit(List<Map<String, String>> git) {
        this.git = git;
    }

    public List<Map<String, String>> getMongo() {
        return mongo;
    }

    public void setMongo(List<Map<String, String>> mongo) {
        this.mongo = mongo;
    }


    @Override
    public String toString() {
        return "ServiceSources{" +
                "git=" + git +
                ", mongo=" + mongo +
                '}';
    }
}
