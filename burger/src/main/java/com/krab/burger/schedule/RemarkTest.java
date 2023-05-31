package com.krab.burger.schedule;

import com.krab.burger.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemarkTest {
    private final MenuMapper menuMapper;

    @Autowired(required = false)
    public RemarkTest(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Scheduled(cron = "0 0 */3 * * ?")
    public void updateDishRemark() {
        // 每三小时执行一次
        System.out.println("=========================Updating================");
        menuMapper.updateDishSale();
        menuMapper.updateDishRemark();
    }
}
