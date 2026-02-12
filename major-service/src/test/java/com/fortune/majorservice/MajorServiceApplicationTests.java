package com.fortune.majorservice;

import com.fortune.majorservice.domain.model.Sys;
import com.fortune.majorservice.infrastructure.mapper.SysMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class MajorServiceApplicationTests {

    @Autowired
    private SysMapper sysMapper;

    @Test
    void contextLoads() {
        List<Sys> list = sysMapper.selectList(null);
        System.out.println("list:" + list);
    }

}
