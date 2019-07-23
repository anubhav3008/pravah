package com.pravah.web.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageHelper {

    Map<String, String>  hashMap= new HashMap<>();
    public String getBaseImageBase64(String path) throws IOException {
        if(hashMap.containsKey(path)){
            return hashMap.get(path);
        }
        InputStream in = new URL(path).openStream();
        Files.copy(in, Paths.get("server.png"), StandardCopyOption.REPLACE_EXISTING);
        byte[] fileContent = FileUtils.readFileToByteArray(new File("server.png"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        hashMap.put(path,"data:image/png;charset=utf-8;base64,"+encodedString);
        return hashMap.get(path);
    }
    public static String toastmastersLogoUrl="http://www.toastmasters.org/resources/logos-images-and-templates/~/media/4BDB0DB436004830BA1DB7780BC6B3A0.ashx";
}
