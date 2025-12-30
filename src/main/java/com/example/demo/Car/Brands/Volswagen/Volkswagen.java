package com.example.demo.Car.Brands.Volswagen;


import com.example.demo.Car.Brand;

import java.util.Arrays;
import java.util.List;

public class Volkswagen implements Brand {
    @Override
    public List<Class<?>> getClasses1() {
        List<Class<?>> classes = Arrays.asList(Golf_VIII.class, Passat_Variant.class, Tiguan_R_Line.class, Touareg_V6.class, Arteon_Shooting_Brake.class, ID3_Pro.class, ID4_GTX.class);
        return classes;
    }
}
