package com.property.propertybooking.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {

    public String uploadImage(MultipartFile file, Long propertyId) {
        try {
            String fileName =
                    "properties/" + propertyId + "/" +
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Bucket bucket = StorageClient.getInstance().bucket();

            Blob blob = bucket.create(
                    fileName,
                    file.getBytes(),
                    file.getContentType()
            );

            return blob.getMediaLink(); // public URL

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
