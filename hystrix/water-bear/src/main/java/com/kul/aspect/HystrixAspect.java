package com.kul.aspect;

import com.kul.model.HystrixModel;
import com.kul.model.HystrixSwitchStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Aspect
public class HystrixAspect {

//    public static final String POINT_CUT = "execution (* com.kul.controller.WBController.doRpc(..))";
    public static Map<String, HystrixModel> hystrixMap = new HashMap<>();

    static {
        hystrixMap.put("order-service",new HystrixModel());
    }

    @Around(value = "@annotation(com.kul.anno.MyHystrix)")
    public Object hystrixAround(ProceedingJoinPoint joinPoint){
        Object result = new Object();
        //获取到当前提供者的断路器
        HystrixModel hystrixModel = hystrixMap.get("order-service");
        HystrixSwitchStatus switchStatus = hystrixModel.getStatus();
        switch (switchStatus){
            case CLOSED:
                //正常情况,去调用
                try {
                    result = joinPoint.proceed();
                    //说明调用成功
                    return result;
                } catch (Throwable e) {
                    //说明调用失败
                    hystrixModel.addFailCount();
                    return "远程服务宕机,所以调用失败了 : " + hystrixModel.getStatus();
                }
            case OPEN:
                //断路器开启,无法调用
                return "开启 : "+hystrixModel.getStatus();

            case HALF_OPEN:
                //分流,少许流量去调用服务
                int i = new Random().nextInt(5);
                if(i == 1) {
                    try {
                        result = joinPoint.proceed();
                        hystrixModel.setStatus(HystrixSwitchStatus.CLOSED);
                        synchronized (hystrixModel) {
                            hystrixModel.notifyAll();
                        }
                        return result;
                    } catch (Throwable e) {
                        return "分流请求,但是调用失败了";
                    }
                }
                return "原因是我" + hystrixModel.getStatus();
            default:
                return "默认情况";
        }
    }
}
