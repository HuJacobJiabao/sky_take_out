package com.sky.utils;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AzureBlobUtil {
    private String connectionString;
    private String containerName;

    public String upload(byte[] bytes, String objectName) {
        try{
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().
                            connectionString(connectionString).buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

            if (!blobContainerClient.exists()) {
                blobContainerClient.create();
            }
            BlobClient blobClient = blobContainerClient.getBlobClient(objectName);

            blobClient.upload(new ByteArrayInputStream(bytes), bytes.length, true);

            String url = blobClient.getBlobUrl();
            log.info("File upload to Azure Blob: {}", url);
            return url;
        } catch (Exception e) {
            log.error("Failed to upload file to Azure Blob", e);
            return null;
        }
    }
}
