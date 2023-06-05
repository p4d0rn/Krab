package com.krab.burger.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.krab.burger.common.Const;
import com.krab.burger.common.Result;
import com.krab.burger.dto.LoginDto;
import com.krab.burger.entity.User;
import com.krab.burger.service.AdminService;
import com.krab.burger.service.UserService;
import com.krab.burger.utils.JwtUtils;
import com.krab.burger.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import com.alibaba.fastjson2.JSONObject;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        System.out.println("=====================================captcha====================");
        String code = producer.createText();
        String key = UUID.randomUUID().toString();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray()).replace("\r\n", "");

        redisUtils.hset(Const.CAPTCHA_KEY + key, code, 120);
        log.info("验证码 -- {} - {}", key, code);

        return Result.success(MapUtil.builder()
                .put("randId", key)
                .put("base64Img", base64Img).map(), "验证码获取成功");
    }

    @PostMapping("/admin/login")
    public Result login(@RequestBody LoginDto loginDto) {
        String code = loginDto.getCode();
        String key = loginDto.getRandId();
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            return Result.error("验证码为空");
        }
        if (!code.equals(redisUtils.hget(Const.CAPTCHA_KEY + key))) {
            return Result.error("验证码错误或已过期");
        }
        redisUtils.hdel(Const.CAPTCHA_KEY + key);
        if (adminService.login(loginDto.getName(), loginDto.getPassword())) {
            String token = jwtUtils.generateToken(MapUtil.builder(new HashMap<String, Object>()).put("openId", "admin").map());
            return Result.success(MapUtil.builder(new HashMap<String, Object>())
                    .put("token", token).map(), "登录成功");
        }
        return Result.error("账号或密码错误");
    }

    @PostMapping("/user/login")
    public Result login(@RequestParam("code") String code) throws IOException {
        String loginUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Const.appId +
                "&secret=" + Const.secret + "&js_code=" + code + "&grant_type=authorization_code";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        // 创建httpGet请求
        HttpGet httpGet = new HttpGet(loginUrl);
        // 发送请求
        client = HttpClients.createDefault();
        // 执行请求
        response = client.execute(httpGet);
        // 得到返回数据
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        response.close();
        client.close();
        // 对返回的结果进行解析
        JSONObject json = JSONObject.parseObject(result);
        String openid = json.getString("openid");
        String sessionKey = json.getString("session_key");
        System.err.println("openid值： " + openid);
        System.err.println("sessionKey值" + sessionKey);

        User user = userService.getUserByOpenid(openid);

        if (StringUtils.isEmpty(openid)) {
            return Result.error("登录失败,openID错误");
        } else {
            String token = jwtUtils.generateToken(MapUtil.builder(new HashMap<String, Object>()).put("openId", openid).map());
            // 判断是否为首次登陆，是则进行注册
            if (user == null) {
                userService.register(openid);
                user = new User();
                user.setUserId(openid);
                user.setUserName("微信用户");
                user.setPoints(0); // 初始化积分0
            }
            return Result.success(MapUtil.builder(new HashMap<String, Object>())
                    .put("user", user)
                    .put("token", token) // 返回JWT
                    .map(), "登录成功");
        }
    }
}
