package com.Carleasing.carleasing.repository;


import com.Carleasing.carleasing.model.CarMake;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface CarMakeRepository {

    CarMake save(MultipartFile multipartFile, CarMake carMake);
    CarMake findCarMake(String makeId);
    String deleteCarMake(String makeId);
    String updateCarMake(MultipartFile multipartFile, CarMake carMake);

    Iterable<CarMake> findAll();
}
