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

    public CarModelDTO criar(CarModelDTO carModelDTO) {
        CarModel carModel = new CarModel();
        carModel.setVersao(carModelDTO.getVersao());
        carModel.setModel(carModelDTO.getModel());
        carModel.setConfig(carModelDTO.getConfig());
        carModel.setConteudo(carModelDTO.getConteudo());
        carModel.setPreco_nacional(carModelDTO.getPreco_nacional());
        carModel.setPreco_ao(carModelDTO.getPreco_ao());
        carModel.setPreco_alc(carModelDTO.getPreco_alc());
        carModel.setPreco_zfm(carModelDTO.getPreco_zfm());
        repository.save(carModel);
        carModelDTO.setId(carModel.getId());
        return carModelDTO;
    }

    public CarModelDTO atualizar(CarModelDTO carModelDTO, Long carModelId) {
        CarModel carModelDatabase = repository.findById(carModelId).orElseThrow(() -> new RuntimeException("Car model not found"));
        carModelDatabase.setVersao(carModelDTO.getVersao());
        carModelDatabase.setModel(carModelDTO.getModel());
        carModelDatabase.setConfig(carModelDTO.getConfig());
        carModelDatabase.setConteudo(carModelDTO.getConteudo());
        carModelDatabase.setPreco_nacional(carModelDTO.getPreco_nacional());
        carModelDatabase.setPreco_ao(carModelDTO.getPreco_ao());
        carModelDatabase.setPreco_alc(carModelDTO.getPreco_alc());
        carModelDatabase.setPreco_zfm(carModelDTO.getPreco_zfm());
        repository.save(carModelDatabase);
        return carModelDTO;
    }

    private CarModelDTO converter(CarModel carModel) {
        CarModelDTO result = new CarModelDTO();
        result.setId(carModel.getId());
        result.setVersao(carModel.getVersao());
        result.setModel(carModel.getModel());
        result.setConfig(carModel.getConfig());
        result.setConteudo(carModel.getConteudo());
        result.setPreco_nacional(carModel.getPreco_nacional());
        result.setPreco_ao(carModel.getPreco_ao());
        result.setPreco_alc(carModel.getPreco_alc());
        result.setPreco_zfm(carModel.getPreco_zfm());
        return result;
    }

    public List<CarModelDTO> getAll() {
        return repository.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    public CarModelDTO getById(Long carModelId) {
        CarModel carModel = repository.findById(carModelId).orElseThrow(() -> new RuntimeException("Car model not found"));
        return converter(carModel);
    }

    public List<CarModelDTO> getByModel(String model) {
        return repository.findByModelContainingIgnoreCase(model).stream().map(this::converter).collect(Collectors.toList());
    }

    public String delete(Long carModelId) {
        repository.deleteById(carModelId);
        return "DELETED";
    }
}
