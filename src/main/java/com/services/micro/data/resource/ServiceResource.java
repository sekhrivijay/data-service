package com.services.micro.data.resource;

import com.services.micro.data.api.request.ServiceRequest;
import com.services.micro.data.api.response.Data;
//import com.services.micro.data.api.response.Error;
import com.services.micro.data.api.response.ServiceResponse;
import com.services.micro.data.bl.DataService;
import com.services.micro.data.util.ResourceUtil;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Controller
@RequestMapping("/files")
public class ServiceResource {

    @Inject
    private DataService dataService;

    @GetMapping("/")
    public void read(WebRequest webRequest, HttpServletResponse httpServletResponse) throws Exception {
        ServiceRequest serviceRequest = ResourceUtil.buildServiceRequest(webRequest.getParameterMap());
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + serviceRequest.getFileName());
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ServiceResponse serviceResponse = dataService.read(serviceRequest);
        IOUtils.copy(serviceResponse.getData().getInputStream(), httpServletResponse.getOutputStream());
        httpServletResponse.flushBuffer();
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<ServiceResponse> create(
            WebRequest webRequest,
            HttpServletRequest httpServletRequest) throws Exception {
        ServiceRequest serviceRequest = ResourceUtil.buildServiceRequest(webRequest.getParameterMap());
        Data data = Data.DataBuilder.aData()
                .withInputStream(getFileInputStream(httpServletRequest))
                .build();
        serviceRequest.setData(data);
        return ResponseEntity.ok()
                .body(dataService.create(serviceRequest));
    }


    @DeleteMapping("/")
    @ResponseBody
    public ResponseEntity<ServiceResponse> delete(WebRequest webRequest) throws Exception {
        ServiceRequest serviceRequest = ResourceUtil.buildServiceRequest(webRequest.getParameterMap());
        return ResponseEntity.ok()
                .body(dataService.delete(serviceRequest));
    }


    @PutMapping("/")
    @ResponseBody
    public ResponseEntity<ServiceResponse> update(WebRequest webRequest,
                                           HttpServletRequest httpServletRequest) throws Exception {
        ServiceRequest serviceRequest = ResourceUtil.buildServiceRequest(webRequest.getParameterMap());
        Data data = Data.DataBuilder.aData()
                .withInputStream(getFileInputStream(httpServletRequest))
                .build();
        serviceRequest.setData(data);
        return ResponseEntity.ok()
                .body(dataService.update(serviceRequest));
    }


//    private ResponseEntity<ServiceResponse> buildUnsuccessfullResponse(String message) {
//        Error error = Error.ErrorBuilder.anError()
//                .withMessage(message)
//                .build();
//        ServiceResponse serviceResponse = ServiceResponse.ServiceResponseBuilder.aServiceResponse()
//                .withError(error)
//                .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(serviceResponse);
//    }


    private InputStream getFileInputStream(HttpServletRequest httpServletRequest) throws Exception {
//        if (!ServletFileUpload.isMultipartContent(httpServletRequest)) {
        if (!isMultipartContent(httpServletRequest)) {
            throw new Exception("File is Empty");
        }
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iter = upload.getItemIterator(httpServletRequest);
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            InputStream stream = item.openStream();
            if (!item.isFormField()) {
                return stream;
            }
        }
        throw new Exception("Could not find file");
    }

    private static boolean isMultipartContent(HttpServletRequest request) {
        return ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod()))
                && FileUploadBase.isMultipartContent(new ServletRequestContext(request));
    }
}