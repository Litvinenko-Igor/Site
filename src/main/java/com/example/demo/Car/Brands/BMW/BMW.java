package com.example.demo.Car.Brands.BMW;



import com.example.demo.Car.Brand;

import java.util.Arrays;
import java.util.List;

public class BMW implements Brand {
    @Override
    public List<Class<?>> getClasses1() {
        List<Class<?>> classes = Arrays.asList(BMW_M2.class, BMW_M3.class, BMW_M4.class, BMW_X4.class, BMW_X5.class, BMW_X7.class, BMW_XM.class);
        return classes;
    }
}
