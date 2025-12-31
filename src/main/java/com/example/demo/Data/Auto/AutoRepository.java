package com.example.demo.Data.Auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {

    List<Auto> findByOwnerId(Long ownerId);

    List<Auto> findByStatus(AutoStatus status);

}
