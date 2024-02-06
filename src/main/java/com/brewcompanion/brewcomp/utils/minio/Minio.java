package com.brewcompanion.brewcomp.utils.minio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.objects.Label;
import com.brewcompanion.brewcomp.utils.Secrets;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.http.Method;
import io.minio.messages.Item;
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
        validateBucket(Main.getConfig().getMinioTemplateBucketName(), true, Main.getConfig().getMinioTemplatePolicy());

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

    public static Optional<Label[]> getLabels(String token) {
        if (minioClient == null) {
            Main.getLogger().error("Minio client is null");
            return Optional.empty();
        }

        if (token == null) {
            Main.getLogger().error("Token is null");
            return Optional.empty();
        }

        if (token.isEmpty()) {
            Main.getLogger().error("Token is empty");
            return Optional.empty();
        }

        try {
            List<Result<Item>> items = new ArrayList<>();
            minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(Main.getConfig().getMinioLabelBucketName())
                    .prefix(token + "/") // Specify the folder path using the token
                    .build()).forEach(items::add);

            // extract the static url from the item
            String[] urls = new String[items.size()];
            for (int i = 0; i < items.size(); i++) {
                urls[i] = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(Main.getConfig().getMinioLabelBucketName())
                        .object(items.get(i).get().objectName()).method(Method.GET)
                        .build());
            }

            Label[] labels = new Label[urls.length];
            for (int i = 0; i < urls.length; i++) {
                String[] split = urls[i].split("/");
                String name = split[split.length - 1];
                // remove everything after the first ?
                name = name.split("\\?")[0];
                labels[i] = new Label(name, urls[i]);
            }

            return Optional.of(labels);
        } catch (Exception e) {
            e.printStackTrace();
            Main.getLogger().error("Failed to get labels from Minio");
            return Optional.empty();
        }
    }

    public static String getTemplate(String name) {
        if (name == null) {
            Main.getLogger().error("Name is null");
            return null;
        }
        if (minioClient == null) {
            Main.getLogger().error("Minio client is null");
            return null;
        }

        name = name + ".html";

        Main.getLogger().info("Getting template from Minio");
        Main.getLogger().info("Name: {}", name);
        


        if (name.isEmpty()) {
            Main.getLogger().error("Name is empty");
            return null;
        }

        try {
            // get the list of all files in the template bucket
            List<Result<Item>> items = new ArrayList<>();
            minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(Main.getConfig().getMinioTemplateBucketName())
                    .build()).forEach(items::add);

            //print the list of files
            for (Result<Item> item : items) {
                Main.getLogger().info(item.get().objectName());
            }

            // find the file with the name
            for (Result<Item> item : items) {
                if (item.get().objectName().equals(name)) {
                    // get the raw html from the file
                    InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                            .bucket(Main.getConfig().getMinioTemplateBucketName())
                            .object(item.get().objectName())
                            .build());
                    byte[] bytes = stream.readAllBytes();

                    // convert the raw html to a string
                    return new String(bytes);
                }
            }

            Main.getLogger().error("Failed to get template from Minio");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            Main.getLogger().error("Failed to get template from Minio");
            return null;
        }
    }

}