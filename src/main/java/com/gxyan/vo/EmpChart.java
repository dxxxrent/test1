package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;

//员工的销售量objectValue
@Data
public class EmpChart {
    private String name;

    private BigDecimal value;
}
