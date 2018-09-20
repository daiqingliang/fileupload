package com.daiql.fileupload.controller;

import com.daiql.fileupload.domain.UploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadController {

    @GetMapping(value = "/")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadResult file_upload(@RequestParam("file") MultipartFile file) {
        UploadResult uploadResult = new UploadResult();
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);

        String saveFileName = file.getOriginalFilename();
        File saveFile = new File("C:/Users/daiql/Desktop/" + saveFileName);
        uploadResult.setFid("111");
        uploadResult.setStatus("success");//status设置为success，前端zui才确认为上传成功
        uploadResult.setSavename(saveFileName);
        uploadResult.setFileUrl("C:/Users/daiql/Desktop/" + saveFileName);

        if (!saveFile.getParentFile().exists()) {
            System.out.println(saveFile.getParentFile().mkdirs());
        }
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            uploadResult.setStatus("failure");
        }

        return uploadResult;
    }

}
