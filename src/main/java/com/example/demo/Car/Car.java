package com.example.demo.Car;

import com.example.demo.Car.Brands.Audi.Audi;
import com.example.demo.Car.Brands.BMW.BMW;
import com.example.demo.Car.Brands.Mercedes.Mercedes;
import com.example.demo.Car.Brands.Volswagen.Volkswagen;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class Car {
    private final Map<String, InfoModel> byModel = new HashMap();
    private final Map<Class<?>, List<InfoModel>> byBrand = new HashMap();
    private List<Brand> brands = new ArrayList();

    @PostConstruct
    public void init() {
        brands = List.of(new Audi(), new BMW(), new Mercedes(), new Volkswagen());
        System.out.println("brand=" + brands.size());
        for(Brand b : brands) {
            List<InfoModel> list = new ArrayList<>();
            for(Class<?> modelClass : b.getClasses1()){
                ModelOfCar ann = modelClass.getAnnotation(ModelOfCar.class);
                if(ann == null) {
                    continue;
                }
                InfoModel infoModel = new InfoModel(ann.brand(), ann.model(), ann.price(), ann.topSpeed(), ann.image(), ann.fuelType(), ann.description());
                byModel.put(ann.model(), infoModel);
                list.add(infoModel);
            }
            byBrand.put(b.getClass(), list);
        }
        System.out.println("byModel=" + byModel.size());
        System.out.println("byBrand=" + byBrand.size());
        brands = List.copyOf(brands);
    }
}