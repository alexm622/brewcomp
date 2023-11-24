package com.brewcompanion.brewcomp.utils;

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

    public static Config loadConfigFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), Config.class);
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

        config.setMinioQrCodePolicy("{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::brewcomp-qrcodes\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::brewcomp-qrcodes/*\"]}]}");
        config.setMinioLabelPolicy("{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::brewcomp-labels\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::brewcomp-labels/*\"]}]}");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), config);
    }
}
