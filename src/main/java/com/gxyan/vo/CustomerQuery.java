package com.gxyan.vo;

import lombok.*;

//查询到的客户objectValue
@Data
public class CustomerQuery {
    private int page = 1;

    private int limit = 5;

    private Long id;

    private String name;

    private String phone;

    private String idCard;

    private String orderBy;
}
