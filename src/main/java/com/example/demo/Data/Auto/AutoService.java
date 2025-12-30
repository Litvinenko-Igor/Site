package com.example.demo.Data.Auto;

import com.example.demo.Data.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AutoService extends CrudService<Auto, Long> {

    private final AutoRepository autoRepository;

    @Override
    protected JpaRepository<Auto, Long> repo() {
        return autoRepository;
    }

    public Auto createAuto(Auto auto) {
        return save(auto);
    }
    @Transactional
    public Auto updateBrandAndModel(Long id, String brand, String model) {
        Auto auto = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto not found"));
        auto.setBrand(brand);
        auto.setModel(model);
        return autoRepository.save(auto);
    }
}
