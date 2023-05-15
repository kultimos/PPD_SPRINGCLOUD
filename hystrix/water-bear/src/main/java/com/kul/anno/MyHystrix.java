package com.kul.anno;

import java.lang.annotation.*;

/**
 * 熔断器切面注解
 */
@Target(ElementType.METHOD) //该注解是要作用在方法上实现切面,所以这里选择的Type是METHOD
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyHystrix {

}
