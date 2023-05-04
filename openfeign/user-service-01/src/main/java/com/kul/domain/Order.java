package com.kul.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //@Builder注解的作用是开启构建者模式
public class Order {
    private Integer id;

    private String name;

    private Double price;

    private Date time;
}
