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

    private Object lock = new Object();

    public ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4,8,30,TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(2000), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 当前断路器失败次数
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    {
        //每当一个断路器创建,就会启动一个线程,用来监控断路器的状态
        //这里会涉及到一个线程池复用的问题
        //因为该线程是一个死循环,所以会一直占用线程池中的一个线程,而不会重新new一个线程去执行
        //并且因为线程池写在静态代码块中,所以随着程序开启,会创建该任务线程,所以该线程会始终复用,每20s执行一次
        //并不会出现每new一个对象就会创建一个线程的情况
        //这里进行了一个优化,如果断路器状态不为CLOSED,则阻塞,直到状态变为CLOSED,才会继续执行
        executor.execute(() -> {
            while (true) {
                try {
                    //每隔20秒,就会进行一次状态的检查
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //如果断路器状态仍为CLOSED,说明近20s时间内,断路器未发生状态变化,则清零单位时间失败次数计数器
                if(this.status.equals(HystrixSwitchStatus.CLOSED)){
                    //如果断路器状态为CLOSED,则清零
                    this.currentFailCount.set(0);
                } else {
                    //这里是阻塞我们当前这个定时任务线程,因为如果当前状态不是close,其实就不需要清零,如果一直跑反而占CPU,不如就阻塞在这里
                    //等分流请求成功,状态变为CLOSED后,该线程会被唤醒,就会继续执行定时清零任务
                        synchronized (lock) {
                        try {
                            //如果断路器状态不为CLOSED,则阻塞
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
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
                //开启一个延时任务,在WINDOW_TIME时间后,将断路器状态从open变为half_open以企图分流请求,待分流请求成功后,再将断路器状态变为CLOSED
                //这里就是开启一个线程执行异步任务,因为这里是需要睡眠的,如果不异步,那就所有都阻塞在这里了,页面也阻塞了,用户啥也看不到
                //现在这样,用户会知道当前服务当机了,但是并不知道断路器是开启状态还是halfopen状态,因为这里是异步的,所以页面会立马返回
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
