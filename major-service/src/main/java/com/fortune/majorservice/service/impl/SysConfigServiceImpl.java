package com.fortune.majorservice.service.impl;

import com.fortune.majorservice.domain.model.Sys;
import com.fortune.majorservice.exception.ResourceNotFoundException;
import com.fortune.majorservice.infrastructure.mapper.SysMapper;
import com.fortune.majorservice.service.SysConfigService;
import com.fortune.majorservice.vo.SysConfigVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    private final SysMapper sysMapper;

    public SysConfigServiceImpl(SysMapper sysMapper) {
        this.sysMapper = sysMapper;
    }

    @Override
    public List<SysConfigVO> listConfigs() {
        return sysMapper.selectList(null)
                .stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public SysConfigVO getConfig(String variable) {
        if (!StringUtils.hasText(variable)) {
            throw new IllegalArgumentException("配置键不能为空");
        }
        Sys sys = sysMapper.selectById(variable);
        if (sys == null) {
            throw new ResourceNotFoundException("未找到对应配置: " + variable);
        }
        return toVO(sys);
    }

    private SysConfigVO toVO(Sys sys) {
        return new SysConfigVO(
                sys.getVariable(),
                sys.getValue(),
                sys.getSetTime(),
                sys.getSetBy()
        );
    }
}
