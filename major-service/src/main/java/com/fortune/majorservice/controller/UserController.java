package com.fortune.majorservice.controller;

import com.fortune.commonservice.feign.common.BeeResponseEntity;
import com.fortune.majorservice.service.SysConfigService;
import com.fortune.majorservice.vo.SysConfigVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SysConfigService sysConfigService;

    public UserController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @GetMapping("/ping")
    public BeeResponseEntity<String> ping() {
        return BeeResponseEntity.buildSuccess("major-service is running");
    }

    @GetMapping("/configs")
    public BeeResponseEntity<List<SysConfigVO>> listConfigs() {
        return BeeResponseEntity.buildSuccess(sysConfigService.listConfigs());
    }

    @GetMapping("/configs/{variable}")
    public BeeResponseEntity<SysConfigVO> getConfig(@PathVariable String variable) {
        return BeeResponseEntity.buildSuccess(sysConfigService.getConfig(variable));
    }
}
