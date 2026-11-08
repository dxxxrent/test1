package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;

//销售量的ObjectValue
@Data
public class SalesChart {
    private String date;

    private BigDecimal income;
    //支出
    private BigDecimal expenditure;
    //利润
    private BigDecimal profit;
}
