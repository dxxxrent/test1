package com.gxyan.vo;

import lombok.Data;

@Data
public class NoticeQuery {
    private int page = 1;
    private int limit = 10;
    private String title;
    private Integer status;
}
