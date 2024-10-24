package com.aps.sistemacarros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aps.sistemacarros.model.Car;
import com.aps.sistemacarros.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car car) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
        existingCar.setModelo(car.getModelo());
        existingCar.setMarca(car.getMarca());
        existingCar.setAno(car.getAno());
        existingCar.setPreco(car.getPreco());
        return carRepository.save(existingCar);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> listCars() {
        return carRepository.findAll();
             
    }
    
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow();
    }

}
