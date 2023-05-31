package com.krab.burger.controller;

import com.krab.burger.common.Result;
import com.krab.burger.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Autowired
    QiniuUtils qiniuUtils;

    @RequestMapping("/upload")
    public Result<String> upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            // 图片上传
            qiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            return Result.success(fileName, "图片上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            //图片上传失败
            return Result.error("图片上传失败");
        }
    }
}
