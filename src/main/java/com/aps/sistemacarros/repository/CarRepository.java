package com.aps.sistemacarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.sistemacarros.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
