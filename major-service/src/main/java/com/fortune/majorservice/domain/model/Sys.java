package com.fortune.majorservice.domain.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@TableName("sys_config")
public class Sys {

    @TableId
    private String variable;

    private String value;

    private LocalDateTime setTime;

    private String setBy;

}
