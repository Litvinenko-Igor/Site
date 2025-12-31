package com.example.demo.Data.Auto;

import com.example.demo.Data.CrudService;
import com.example.demo.Data.User.User;
import com.example.demo.Data.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AutoService extends CrudService<Auto, Long> {

    private final AutoRepository autoRepository;
    private final UserRepository userRepository;

    @Override
    protected JpaRepository<Auto, Long> repo() {
        return autoRepository;
    }

    public List<Auto> getMarket() {
        return autoRepository.findByStatus(AutoStatus.FOR_SALE);
    }

    public List<Auto> getMyAuto(Long userId) {
        return autoRepository.findByOwnerId(userId);
    }

    public List<Auto> getAutos(){
        return autoRepository.findAll();
    }

    @Transactional
    public Auto createAutoForUser(Long userId,
                                  String brand,
                                  String model,
                                  double price,
                                  int topSpeed,
                                  MultipartFile image,
                                  String fuelType,
                                  String description) {

        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String imagePath = saveImage(image);

        Auto auto = new Auto();
        auto.setBrand(brand);
        auto.setModel(model);
        auto.setPrice(price);
        auto.setTopSpeed(topSpeed);
        auto.setImagePath(imagePath);
        auto.setFuelType(fuelType);
        auto.setDescription(description);
        auto.setOwner(owner);
        auto.setStatus(AutoStatus.FOR_SALE);

        return autoRepository.save(auto);
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }

        try {
            String original = file.getOriginalFilename();
            String ext = "";

            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }

            String filename = UUID.randomUUID() + ext;

            Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);


            Path target = uploadDir.resolve(filename).normalize();

            System.out.println("UPLOAD DIR = " + uploadDir.toAbsolutePath());
            System.out.println("TARGET = " + target.toAbsolutePath());

            file.transferTo(target.toFile());

            return "/uploads/" + filename;

        } catch (Exception e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
