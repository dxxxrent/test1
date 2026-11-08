package com.gxyan.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//车系的ObjectValue
@Getter
@Setter
public class SeriesTree {
    private Integer value;
    private String label;
    private List<SeriesTree> children;
}
