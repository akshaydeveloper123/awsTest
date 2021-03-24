package com.example.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestingController {

    List<String> list=new ArrayList<>();

    @PostMapping("/name")
    public String getName(@RequestParam("first_name") String first_name, @RequestParam("last_nae") String last_name){

        return first_name+" "+last_name;

    }

    @GetMapping("/list")
    public List<String> getlist(){
        list.add("Test1");
        list.add("Test2");
        list.add("Test3");
        list.add("Test4");

        return list;

    }

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile file)
    {
        this.amazonS3ClientService.uploadFileToS3Bucket(file, true);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

        return response;
    }

    @DeleteMapping("/del")
    public Map<String, String> deleteFile(@RequestParam("file_name") String fileName)
    {
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

        return response;
    }
}
