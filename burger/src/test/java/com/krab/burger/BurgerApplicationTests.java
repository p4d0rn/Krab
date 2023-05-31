package com.krab.burger;

import cn.hutool.crypto.SecureUtil;
import com.krab.burger.entity.Dish;
import com.krab.burger.entity.DishCated;
import com.krab.burger.entity.User;
import com.krab.burger.mapper.UserMapper;
import com.krab.burger.service.DishService;
import com.krab.burger.service.UserService;
import com.krab.burger.utils.QiniuUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@SpringBootTest
class BurgerApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    DishService dishService;

    @Autowired
    QiniuUtils qiniuUtils;

    @Test
    void menuTest(){
        List<DishCated> dishCateds = dishService.getMenu();
        System.out.println(dishCateds);
    }

    public static byte[] file2Byte(String filePath) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBytes;
    }

    @Test
    void FileTest(){
        qiniuUtils.upload2Qiniu(file2Byte("C:\\Users\\PANDA\\Pictures\\images.png"), "test.png");
    }

}
