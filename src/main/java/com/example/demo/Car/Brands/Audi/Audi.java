package com.example.demo.Car.Brands.Audi;



import com.example.demo.Car.Brand;
import lombok.Getter;

import java.util.*;
@Getter
public class Audi implements Brand {
    @Override
    public List<Class<?>> getClasses1() {
        List<Class<?>> classes = Arrays.asList(Audi_A1.class, Audi_A3.class, Audi_A5.class, Audi_Etron_GT.class, Audi_Q7.class, Audi_S6.class, Audi_S8.class);
        return classes;
    }
}
