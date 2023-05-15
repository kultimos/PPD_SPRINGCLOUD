package com.kul.model;

import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 断路器模型
 */
@Data
public class HystrixModel {
    /**
     * 这个断路器的类创建是有说法的,看他的多个变量中private和public的设计理解
     * status和currentFailCount都是私有的，这是因为每个断路器都只有一个自己的状态和失败次数
     * 而 WINDOW_TIME 和 MAX_FAIL_COUNT则是断路器的通性，所以是公有的
     */

    /**
     * 断路器有它自己的状态
     */
    private HystrixSwitchStatus status = HystrixSwitchStatus.CLOSED;

    /**
     * 单位时间,在该单位时间内一直无法请求通,则熔断
     */
    public static final Integer WINDOW_TIME = 20;

    /**
     * 最大失败次数,单位时间内达到最大失败次数,则熔断
     */
    public static final Integer MAX_FAIL_COUNT = 3;

    public ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4,8,30,TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(2000), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 当前断路器失败次数
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    {
        executor.execute(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.currentFailCount.set(0);
            }
        });
    }

    public void addFailCount(){
        int i = currentFailCount.incrementAndGet();
        if(i >= MAX_FAIL_COUNT){
            //失败次数达到阈值
            //修改断路器状态为OPEN
            this.setStatus(HystrixSwitchStatus.OPEN);

            //等待一个时间窗口后,让断路器变为半开 为了避免多线程访问阻塞的问题,采用线程池异步处理后续问题
            executor.execute(() -> {
                //一个线程专门用来清0,每隔20秒,就会进行一次失败次数的清零,保证单位时间内失败次数达到阈值才会触发后续动作
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(WINDOW_TIME);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.setStatus(HystrixSwitchStatus.HALF_OPEN);
                    this.currentFailCount.set(0);
                }
            });
        }
    }


}
