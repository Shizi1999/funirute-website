package com.poly.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.exception.CloudinaryException;
import com.poly.payload.response.CloudinaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    public static final Logger log = LoggerFactory.getLogger(CloudinaryService.class);
    @Autowired
    Cloudinary cloudinary;

    public CloudinaryResponse uploadFile(MultipartFile file) {
        try {
            Map result= cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("url");
            String id = (String) result.get("public_id");
            return new CloudinaryResponse(id, url);
        }catch (Exception ex){
            throw new CloudinaryException("Upload error");
        }
    }

    public CloudinaryResponse uploadFile(MultipartFile file,String folder) {
        try {
            Map result= cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folder));
            String url = (String) result.get("url");
            String id = (String) result.get("public_id");
            return new CloudinaryResponse(id, url);
        }catch (Exception ex){
            throw new CloudinaryException("Upload error");
        }
    }

    @Async("taskExecutor")
    public void deleteFile(String cloudinaryId) {
        try {
           cloudinary.uploader().destroy(cloudinaryId, ObjectUtils.emptyMap());
        }catch (Exception ex){
            log.warn("Cannot delete image with id:" + cloudinaryId);
        }
    }
}
