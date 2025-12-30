package com.example.demo.Car.Brands.Mercedes;


import com.example.demo.Car.Brand;

import java.util.Arrays;
import java.util.List;

public class Mercedes implements Brand {
    @Override
    public List<Class<?>> getClasses1() {
        List<Class<?>> classes = Arrays.asList(A_200.class, C_300.class, E_350.class, GLC_300.class, GLE_450.class, S_500.class, AMG_GT_63_S.class);
        return classes;
    }
}
