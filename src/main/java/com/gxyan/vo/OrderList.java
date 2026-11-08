package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderList {
    private Long orderId;

    private Long customerId;

    private String customerName;

    private String customerPhone;

    private String customerIdCard;

    private String employeeName;

    private BigDecimal totalPrice;

    private String status;

    private Date createTime;

    private Date payTime;

    private Date updateTime;

    private List<Details> details;
}
