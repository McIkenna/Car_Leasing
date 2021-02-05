package com.Carleasing.carleasing.controller;


import com.Carleasing.carleasing.model.CarMake;
import com.Carleasing.carleasing.service.CarMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
@RequestMapping("/api/carMake")
@CrossOrigin
public class CarMakeController {



    @Autowired
    CarMakeService carMakeService;

    @PostMapping("/admin")
    public CarMake saveCarMake(@RequestParam(value = "file") MultipartFile file, CarMake carMake){
        return carMakeService.save(file, carMake);
    }

    @GetMapping("/user/{makeId}")
    public CarMake findCarMake(@PathVariable String makeId){
        return carMakeService.findCarMake(makeId);
    }

    @DeleteMapping("/admin/{makeId}")
    public String deleteCarMake(@PathVariable String makeId){
        return carMakeService.deleteCarMake(makeId);
    }

    @PutMapping("/admin")
    public String updateCarMake(@RequestParam(value = "file") MultipartFile file, CarMake carMake){
        return carMakeService.updateCarMake(file, carMake);
    }

    @GetMapping("/user/all")
    public Iterable<CarMake> getAllCarMake(){
        return carMakeService.findAll();
    }
}

