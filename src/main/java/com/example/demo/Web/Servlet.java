package com.example.demo.Web;

import com.example.demo.Car.Brand;
import com.example.demo.Car.Car;
import com.example.demo.Car.InfoModel;
import com.example.demo.Car.ModelOfCar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
public class Servlet {
    Car car;
    List<Brand> brandsCar;
    public Servlet(Car car){
        this.car = car;
        this.brandsCar = car.getBrands();
    }
    @GetMapping("/main")
    public String showCar(@RequestParam("click") String click, Model model){
        InfoModel infoModel = car.getByModel().get(click);
        if(infoModel == null){
            for(Brand brand : brandsCar){
                String brandCar = brand.getClass().getSimpleName();
                if(brandCar.equals(click)){
                    List<Map<String, Object>> brands = brand.getClasses1().stream().map(clazz -> {
                        ModelOfCar ann = clazz.getAnnotation(ModelOfCar.class);
                        Map<String, Object> map = new HashMap<>();
                        map.put("brand", brand);
                        map.put("model", ann.model());
                        map.put("price", ann.price());
                        map.put("topSpeed", ann.topSpeed());
                        map.put("image", ann.image());
                        map.put("fuelType", ann.fuelType());
                        map.put("description", ann.description());
                        return map;
                    }).toList();
                    System.out.println("car");
                    model.addAttribute("brands", brands);
                    break;
                }
            }
            return "сar";
        } else {
            model.addAttribute("infoModel", infoModel);
            return "model";
        }
    }
}
