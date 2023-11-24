package com.brewcompanion.brewcomp.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.brewcompanion.brewcomp.Main;

public class Secrets {
    private static final String SECRETS_FILE_PATH = "./secrets.csv";
    private static Map<String, String> secrets;

    private Secrets() {
        // private constructor to hide the implicit public one
    }

    static {
        Main.getLogger().info("Reading secrets file...");
        secrets = new HashMap<>();
        loadSecrets();
        Main.getLogger().info("Secrets file read.");
    }

    private static void loadSecrets() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SECRETS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    secrets.put(key, value);
                }
            }
        } catch (IOException e) {
            Main.getLogger().error("Failed to read secrets file.");
            e.printStackTrace();
        }
    }

    public static String getSecret(String key) {
        return secrets.get(key);
    }
}
