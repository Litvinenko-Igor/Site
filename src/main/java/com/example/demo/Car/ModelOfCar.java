package com.example.demo.Car;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelOfCar {
    String brand() default "";
    String model() default "";
    double price() default 0;
    int topSpeed() default 0;
    String image() default "";
    String fuelType() default "";
    String description() default "";
}
