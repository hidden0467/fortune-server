package com.fortune.majorservice.service;

import com.fortune.majorservice.vo.SysConfigVO;

import java.util.List;

public interface SysConfigService {

    List<SysConfigVO> listConfigs();

    SysConfigVO getConfig(String variable);
}
