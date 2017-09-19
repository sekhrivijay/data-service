# data-service

Currently it is backed up by mongo only. I have left implementation for GIT, S3, FTP, minio , etc . We can add them as we go along. 


It maintains  a map of service to backend persistence store . If it cannot find entry in the map,  it defaults to mongo implementation. Here is how it looks

….
Here is how the factory lookup of implementation is
 
https://github.com/sekhrivijay/data-service/blob/master/src/main/java/com/services/micro/data/bl/crud/DataRepositoryServiceFactory.java



It supports all CRUD operations and any file size
## Create operation :
Post a big  file called jprofiler_linux_7_2_1.tar.gz and name it myfile.txt and assign meta data serviceName demo and environment BETA to it like this
```bash
curl -i -X POST -H "Content-Type: multipart/form-data" -F "file=@jprofiler_linux_7_2_1.tar.gz" 'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev'
``` 
If you see response like this with new id , it would imply your file is persisted .
```json
{"metaData":{"serviceName":"demo","fileName":"myfile.txt","environment":"dev","timeStamp":1498753690883,"version":1},"data":{"status":"SUCCESSFUL","id":"59552a99d0634b4232793eb9","length":0},"statusCode":0}
```

In any other error case you would get exception back
```bash
curl -i -X POST -H "Content-Type: multipart/form-data" -F "file=@jprofiler_linux_7_2_1.tar.gz" 'http://localhost:8080/data/files/?fileName=myfile.txt&environment=dev'
``` 

```json
{"timestamp":1498754408880,"status":500,"error":"Internal Server Error","exception":"java.lang.Exception","message":" Invalid Request. serviceName or fileName or environment is missing 
 ","path":"/data/files/"}
``` 
 
## Read Operation :
Get the big file back just by changing the HTTP method and saving the file like this
```bash
curl -s  -X GET  'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev' > myfile
 
[ ~]$ ls -lrth myfile
-rw-rw-r--. 1 user group 51M Jun 29 11:56 myfile
 
[ ~]$ ls -lrth jprofiler_linux_7_2_1.tar.gz
-rw-r--r--. 1 user user 51M Dec  5  2012 jprofiler_linux_7_2_1.tar.gz
``` 
Confirm that the file upload is same as file downloaded

```bash
[ ~]$ diff jprofiler_linux_7_2_1.tar.gz myfile
 ```
 
## Delete Operation:
Delete any file from the persistence  store by just changing the HTTP method
```bash
curl -s  -X DELETE  'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev'
```
It will also return response like this
```json
{"metaData":{"serviceName":"demo","fileName":"myfile.txt","environment":"dev","timeStamp":1498755633742,"version":1},"data":{"status":"SUCCESSFUL","length":0},"statusCode":0}
```

Confirm by trying to read the file again and you should get an exception message
```bash
curl -s  -X GET  'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev'
```

```json
{"timestamp":1498755714465,"status":500,"error":"Internal Server Error","exception":"java.lang.Exception","message":"File Not found in MongoDB","path":"/data/files/"}
```
 
## Update Operation:
This will delete the existing file from the persistence store and add a new one
```bash
echo "ABCD" > dummy.txt
curl -i -X PUT -H "Content-Type: multipart/form-data" -F "file=@dummy.txt" 'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev'
```

```json
{"metaData":{"serviceName":"demo","fileName":"myfile.txt","environment":"dev","timeStamp":1498755808106,"version":1},"data":{"status":"SUCCESSFUL","id":"595532e0d0634b6e7d2be010","length":0},"statusCode":0}
```
 
Confirm by the reading the file again to see if the contents are updated
```bash
curl -s  -X GET  'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev' > ABCD
 ```

I added one additional feature for this service now. We could now attach any meta data using the same api when uploading or updating the files. Similarly we could query with any meta data when downloading or deleting the files.

For example
```bash
curl -i -X PUT -H "Content-Type: multipart/form-data" -F "file=@dummy.txt" 'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev&mydata=abc&mydata2=xyz'
```

This will attach all fields passed to the API along  with mandatory ones (filename, serviceName and environment) as meta data while storing the file in mongo
Similarly we can query it back by passing any parameters along with the mandatory ones
```bash
curl -s  -X GET  'http://localhost:8080/data/files/?serviceName=demo&fileName=myfile.txt&environment=dev&mydata=abc&mydata2=xyz' > myfile
```

This way in future we can attach whatever we want as meta data to the file.
 
 
To use the Data service to fetch your files within microservice here is how to do it
  
Get the latest  common-helper dependency in pom file
```xml
  <dependency>
      <groupId>com.services.micro</groupId>
       <artifactId>data</artifactId>
	<version>0.0.1</version>
       <classifier>client</classifier>

  </dependency>
```
 
 
 
Set the url for the data-service in application.yml file
```yaml
service:
   data:
     base-url: 'http://localhost:8080/data/files/'
```
 
Inject the DataServiceClient like this in your code somewhere
 
```java
@Inject
 private DataServiceClient dataServiceClient;
 
 
Setup a scheduled code that runs every 12 hours or whatever the period we want using this annotation
@Scheduled(fixedRate = 50000)
 public void fetchFile(String fileNameRead, String fileNameWrite) {
     try {
         dataServiceClient.fetchFile(fileNameRead, fileNameWrite, null);
     } catch (Exception e) {
         logger.error("Failed to get file ", e);
     }
 } 
 
```

The above code will automatically use serviceName and environment and fileNameRead to query for that file and once found will stream it locally and copy it to fileNameWrite
 
 
The 3rd parameter which is null can be used to add more tuples for querying the files . Here is an example. Use this only when we know more meta data was added while inserting the file at the first place.
```java
Map<String, String> metaData = new HashMap<>();
 metaData.put("MyKey", "MyValue");
 dataServiceClient.fetchFile(fileNameRead, fileNameWrite, metaData);
```
 
