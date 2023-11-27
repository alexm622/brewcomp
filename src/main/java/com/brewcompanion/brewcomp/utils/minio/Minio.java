package com.brewcompanion.brewcomp.utils.minio;

import java.io.InputStream;
import java.util.Arrays;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.utils.Secrets;
import com.google.common.base.Optional;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import lombok.Getter;

/**
 * Minio helper class
 */
public class Minio {

    @Getter
    private static MinioClient minioClient;

    public static void initialize() {
        Main.getLogger().info("Initializing Minio client");

        String accessKey = Secrets.getSecret("MINIO_ACCESS_KEY");
        String secretKey = Secrets.getSecret("MINIO_SECRET_KEY");

        if (accessKey == null || secretKey == null) {
            Main.getLogger().error("Minio access key or secret key is null");
            return;
        }

        String endpoint = Main.getConfig().getMinioUrl();

        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        // check to see if the bucket exists
        validateBucket(Main.getConfig().getMinioQrCodeBucketName(), true, Main.getConfig().getMinioQrCodePolicy());
        validateBucket(Main.getConfig().getMinioLabelBucketName(), true, Main.getConfig().getMinioLabelPolicy());

    }

    private static void validateBucket(String bucketName, boolean create, String policy) {
        if (minioClient == null) {
            Main.getLogger().error("Minio client is null");
            return;
        }

        if (bucketName == null) {
            Main.getLogger().error("Bucket name is null");
            return;
        }

        if (bucketName.isEmpty()) {
            Main.getLogger().error("Bucket name is empty");
            return;
        }

        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                Main.getLogger().info("Minio bucket exists");
            } else {
                Main.getLogger().error("Minio bucket does not exist");

                if (!create) {
                    return;
                }

                Main.getLogger().info("Creating Minio bucket");

                // create the bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

                // apply the policy
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder()
                                .bucket(bucketName)
                                .config(policy)
                                .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Main.getLogger().error("Minio bucket does not exist");
        }
    }

    public static void uploadImage(InputStream imageData, long size, String fileName, String bucketName, String path) {
        if (minioClient == null) {
            Main.getLogger().error("Minio client is null");
            return;
        }

        if (imageData == null) {
            Main.getLogger().error("Image data is null");
            return;
        }

        if (fileName == null) {
            Main.getLogger().error("File name is null");
            return;
        }

        if (fileName.isEmpty()) {
            Main.getLogger().error("File name is empty");
            return;
        }

        if (bucketName == null) {
            Main.getLogger().error("Bucket name is null");
            return;
        }

        if (bucketName.isEmpty()) {
            Main.getLogger().error("Bucket name is empty");
            return;
        }

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path + "/" + fileName)
                    .stream(imageData, size, -1)
                    .build());
            Main.getLogger().info("Image uploaded to Minio");

        } catch (Exception e) {
            e.printStackTrace();
            Main.getLogger().error("Failed to upload image to Minio");
        }
    }

    public static void uploadImage(InputStream imageData, long size, String fileName, Buckets bucket) {
        if (minioClient == null) {
            Main.getLogger().error("Minio client is null");
            return;
        }

        if (imageData == null) {
            Main.getLogger().error("Image data is null");
            return;
        }

        if (fileName == null) {
            Main.getLogger().error("File name is null");
            return;
        }

        if (fileName.isEmpty()) {
            Main.getLogger().error("File name is empty");
            return;
        }

        if (bucket == null) {
            Main.getLogger().error("Bucket is null");
            return;
        }

        uploadImage(imageData, size, fileName, bucket.getBucketName(), "/");
    }

}