package com.krab.burger.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class QiniuUtils {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    public void upload2Qiniu(byte[] bytes, String fileName){
        //构造一个带指定Zone对象的配置类
        Zone zone = (new Zone.Builder()).region("z0").upHttp("http://up-cn-east-2.qiniup.com").upHttps("https://up-cn-east-2.qiniup.com").upBackupHttp("http://up-cn-east-2.qiniup.com").upBackupHttps("https://up-cn-east-2.qiniup.com").build();
        Configuration cfg = new Configuration(zone);

        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                ex2.printStackTrace();
            }
        }
    }
}
