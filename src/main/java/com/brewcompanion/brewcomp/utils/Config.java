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

    //redis specific config
    // TODO add redis config

    //minio specific config
    private String minioQrCodeBucketName;
    private String minioLabelBucketName;

    public static Config loadConfigFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), Config.class);
    }
}
