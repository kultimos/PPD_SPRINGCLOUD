package com.kul.model;

/**
 * 该枚举类用于控制 断路器的状态
 */
public enum HystrixSwitchStatus {

    CLOSED, // 关闭
    OPEN, // 开启
    HALF_OPEN // 半开,会进行分流
}
