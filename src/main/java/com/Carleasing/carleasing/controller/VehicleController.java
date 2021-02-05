package com.Carleasing.carleasing.controller;

import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
@RequestMapping("/api/vehicle")
@CrossOrigin
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PostMapping("/admin/{makeId}")
    public Vehicle saveVehicle(@RequestParam(value = "file") MultipartFile file, Vehicle vehicle, @PathVariable String makeId){
        return vehicleService.save(file, vehicle, makeId);
    }

    @GetMapping("/user/{makeId}")
    public Iterable<Vehicle> findVehicle(@PathVariable String makeId){
        return vehicleService.findVehicle(makeId);
    }

    @GetMapping("/user/{makeId}/{vehicleId}")
    public Vehicle findVehicleByMakeId(@PathVariable String makeId, @PathVariable String vehicleId){
        return vehicleService.findVehicleByMakeId(makeId, vehicleId);
    }

    @DeleteMapping("/admin/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId){
        return vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping("/admin")
    public String updateVehicle(@RequestParam(value = "file") MultipartFile file,Vehicle vehicle){
        return vehicleService.updateVehicle(file, vehicle);
    }

    @GetMapping("/all")
    public Iterable<Vehicle> getAllVehicle(){
        return vehicleService.findAll();
    }
}
