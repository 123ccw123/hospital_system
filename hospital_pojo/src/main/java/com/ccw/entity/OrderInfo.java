package com.ccw.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OrderInfo implements Serializable {
    private String name;
    private String sex;
    private String telephone;
    private String idCard;
    private LocalDate orderDate;
    private Integer setmealId;
}
