package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.DatasetDeletionException;
import io.github.nayetdet.insightvault.exception.DatasetUploadException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class MinioService {

    @Value("${minio.bucket}")
    private String minioBucket;

    private final MinioClient minioClient;

    public String create(MultipartFile file) {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(minioBucket)
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return filename;
        } catch (Exception e) {
            log.error("Failed to upload file '{}': {}", filename, e.getMessage(), e);
            throw new DatasetUploadException();
        }
    }

    public void delete(String filename) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(minioBucket)
                            .object(filename)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to delete file '{}': {}", filename, e.getMessage(), e);
            throw new DatasetDeletionException();
        }
    }

}
