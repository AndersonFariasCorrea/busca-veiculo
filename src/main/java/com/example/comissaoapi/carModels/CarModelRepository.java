package com.example.comissaoapi.carModels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByModel(String model);
    List<CarModel> findByModelContainingIgnoreCase(String model);
}
