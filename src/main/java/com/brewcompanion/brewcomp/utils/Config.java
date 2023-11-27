package com.brewcompanion.brewcomp.utils;

import com.brewcompanion.brewcomp.Main;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

import java.io.File;
import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Config {
    private String mysqlUrl;
    private String redisUrl;
    private String minioUrl;

    //mysql specific config
    private String mysqlDbName;
    private Integer mysqlMaxPoolSize;

    //redis specific config
    // TODO add redis config

    //minio specific config
    private String minioQrCodeBucketName;
    private String minioLabelBucketName;

    private String minioQrCodePolicy;
    private String minioLabelPolicy;

    private String minioQrCodePolicyJson;
    private String minioLabelPolicyJson;

    public static Config loadConfigFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = null;
        try {
            config = objectMapper.readValue(new File(filePath), Config.class);
        } catch (IOException e) {
            Main.getLogger().error("Failed to load config from file: {}", e.getMessage());
            System.exit(1);
        }

        try {
            File f = new File(config.getMinioQrCodePolicy());
            config.setMinioQrCodePolicyJson(objectMapper.readTree(f).toString());
        } catch (IOException e) {
            Main.getLogger().error("Failed to read Minio QR code policy file: {}", e.getMessage());
        }

        try {
            File f = new File(config.getMinioLabelPolicy());
            config.setMinioLabelPolicyJson(objectMapper.readTree(f).toString());
        } catch (IOException e) {
            Main.getLogger().error("Failed to read Minio label policy file: {}", e.getMessage());
        }

        return config;
    }

    public static void generateConfigFile(String filePath) throws IOException {
        Config config = new Config();

        //mysql

        config.setMysqlUrl("jdbc:mysql://172.20.0.2:3306/brewcomp");
        config.setMysqlDbName("brewcomp");
        config.setMysqlMaxPoolSize(10);

        //redis
        config.setRedisUrl("redis://172.20.0.3:6379");

        //minio
        config.setMinioUrl("http://172.20.0.4:9000");
        config.setMinioQrCodeBucketName("brewcomp-qrcodes");
        config.setMinioLabelBucketName("brewcomp-labels");

        config.setMinioQrCodePolicy("./misc/minio/qrcode-policy.json");
        config.setMinioLabelPolicy("./misc/minio/label-policy.json");

        config.setMinioQrCodePolicyJson("{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": {\n" +
                "                \"AWS\": [\n" +
                "                    \"*\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetBucketLocation\",\n" +
                "                \"s3:ListBucket\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::brewcomp-qrcodes\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"Sid\": \"\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": {\n" +
                "                \"AWS\": [\n" +
                "                    \"*\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:PutObject\",\n" +
                "                \"s3:DeleteObject\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::brewcomp-qrcodes/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        
                        config.setMinioLabelPolicyJson("{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": {\n" +
                "                \"AWS\": [\n" +
                "                    \"*\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetBucketLocation\",\n" +
                "                \"s3:ListBucket\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::brewcomp-labels\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"Sid\": \"\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": {\n" +
                "                \"AWS\": [\n" +
                "                    \"*\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:PutObject\",\n" +
                "                \"s3:DeleteObject\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::brewcomp-labels/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), config);
    }
}
