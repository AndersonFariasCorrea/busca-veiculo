package com.example.comissaoapi.carModels.facade;

import com.example.comissaoapi.carModels.CarModel;
import com.example.comissaoapi.carModels.CarModelRepository;
import com.example.comissaoapi.carModels.dto.CarModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelFacade {
    @Autowired
    private CarModelRepository repository;

    public CarModelDTO criar(CarModelDTO carModeDTO) {
        CarModel carModel = new CarModel();
        carModel.setVersao(carModeDTO.getVersao());
        repository.save(carModel);
        carModeDTO.setId(carModel.getId());
        return carModeDTO;
    }

    public CarModelDTO atualizar(CarModelDTO carModelDTO, Long carModelId) {
        CarModel carModelDatabase = repository.getOne(carModelId);
        carModelDatabase.setVersao(carModelDTO.getVersao());
        repository.save(carModelDatabase);
        return carModelDTO;
    }

    private CarModelDTO converter(CarModel carModel) {
        CarModelDTO result = new CarModelDTO();
        result.setId(carModel.getId());
        result.setVersao(carModel.getVersao());
        return result;
    }

    public List<CarModelDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public String delete(Long carModeId) {
        repository.deleteById(carModeId);
        return "DELETED";
    }
}
