package com.api_food.Algaworks_Food.infra.storage;

import com.api_food.Algaworks_Food.domain.exception.custom.PhotoStorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BackblazePhotoStorage implements PhotoStorage {

    @Value("${BACKBLAZE_KEY_ID}")
    private String keyId;

    @Value("${BACKBLAZE_APP_KEY}")
    private String appKey;

    @Value("${BACKBLAZE_BUCKET_NAME}")
    private String bucketName;

    @Value("${BACKBLAZE_ENDPOINT}")
    private String endpoint;

    private S3Client s3Client;

    @PostConstruct
    public void init(){

        AwsBasicCredentials credentials = AwsBasicCredentials.create(keyId, appKey);

        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_2)
                .endpointOverride(java.net.URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

    }

    @Override
    public String save(MultipartFile file, int productId) {

        String fileName = productId + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(
                    file.getInputStream(), file.getSize()
            ));
            return fileName;
        } catch (Exception e) {
            throw new PhotoStorageException(e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(request);
        } catch (Exception e){
            throw new PhotoStorageException(e);
        }
    }

    @Override
    public String getUrl(String url) {
        String base = endpoint.startsWith("https://") ? endpoint.substring(8) : endpoint;
        return "https://" + base + "/" + bucketName + "/" + url;
    }
}
