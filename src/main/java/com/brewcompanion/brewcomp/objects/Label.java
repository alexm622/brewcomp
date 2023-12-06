package com.brewcompanion.brewcomp.objects;

import java.io.Serializable;

import lombok.Data;

@Data
public class Label implements Serializable{
    private String name;
    private String url;

    public Label(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
