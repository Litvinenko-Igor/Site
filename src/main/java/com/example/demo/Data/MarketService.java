package com.example.demo.Data;

import com.example.demo.Data.Auto.Auto;
import com.example.demo.Data.Auto.AutoRepository;
import com.example.demo.Data.Auto.AutoStatus;
import com.example.demo.Data.User.User;
import com.example.demo.Data.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MarketService {

    private final AutoRepository autoRepository;
    private final UserRepository userRepository;


    public void putForSale(Long autoId, Long userId) {
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto not found"));

        if (auto.getOwner() == null || !auto.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("That isn't your car");
        }

        auto.setStatus(AutoStatus.FOR_SALE);
    }

    public void removeFromSale(Long autoId, Long userId) {
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto not found"));

        if (auto.getOwner() == null || !auto.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("Це не твоя машина");
        }

        auto.setStatus(AutoStatus.NOT_FOR_SALE);
    }

    @Transactional
    public void buy(Long autoId, Long buyerId){
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new RuntimeException("Auto not found"));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (auto.getStatus() != AutoStatus.FOR_SALE) {
            throw new IllegalArgumentException("This auto isn't for sale");
        }

        if (auto.getOwner() == null) {
            throw new IllegalArgumentException("Auto has no owner");
        }

        if (auto.getOwner().getId().equals(buyerId)) {
            throw new IllegalArgumentException("You can't buy your own auto");
        }

        if(auto.getOwner().getId().equals(buyer.getId())){
            throw new IllegalArgumentException("");
        }

        User seller = auto.getOwner();

        if(buyer.getBudget() >= auto.getPrice()){
            buyer.setBudget(buyer.getBudget() - auto.getPrice());
            seller.setBudget(seller.getBudget() + auto.getPrice());
            auto.setOwner(buyer);
        } else {
            throw new IllegalArgumentException("Not enough budget");
        }

        auto.setStatus(AutoStatus.NOT_FOR_SALE);
        autoRepository.save(auto);
    }
}
