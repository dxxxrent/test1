package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class OrderVo {
    private Long customerId;

    private Integer employeeId;

    private String status;

    private BigDecimal totalPrice;
    //订单详细的objectValue数据
    private List<OrderDetailVo> detailVos;
}
