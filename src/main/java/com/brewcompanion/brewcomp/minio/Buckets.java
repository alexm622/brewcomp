package com.brewcompanion.brewcomp.minio;

import lombok.Getter;

public enum Buckets {
    QR_CODE("brewcomp-qrcodes"),
    LABEL("brewcomp-labels");

    @Getter
    private String bucketName;

    Buckets(String name) {
        this.bucketName = name;
    }
}