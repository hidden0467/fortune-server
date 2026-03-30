package com.fortune.majorservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigVO {

    private String variable;

    private String value;

    private LocalDateTime setTime;

    private String setBy;
}
