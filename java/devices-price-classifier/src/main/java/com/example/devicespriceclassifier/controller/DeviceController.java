package com.example.devicespriceclassifier.controller;

import com.example.devicespriceclassifier.model.Device;
import com.example.devicespriceclassifier.service.DeviceService;
import com.example.devicespriceclassifier.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class DeviceController {

    private static final Logger LOGGER = Logger.getLogger(DeviceController.class.getName());

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceService.getDeviceById(id);
        return device.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/devices/new")
    public Device addDevice(@RequestBody Device device) {
        return deviceService.saveDevice(device);
    }

    @PostMapping("/devices/new/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Device> addDevices(@RequestBody List<Device> devices) {
        return deviceService.saveDevices(devices);
    }

    @PostMapping("/predict/{id}")
    public ResponseEntity<?> predictDevicePrice(@PathVariable Long id) {
        Optional<Device> deviceOpt = deviceService.getDeviceById(id);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            String predictionStr;
            try {
                predictionStr = predictionService.predictPrice(device);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error calling prediction API", e);
                return ResponseEntity.status(500).body("Error calling prediction API: " + e.getMessage());
            }

            try {
                // Remove the brackets and parse the integer
                predictionStr = predictionStr.replaceAll("[\\[\\]]", "");
                int prediction = Integer.parseInt(predictionStr);
                device.setPrice_range(prediction);
                deviceService.saveDevice(device);
                return ResponseEntity.ok(device);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Error parsing prediction response", e);
                return ResponseEntity.status(500).body("Error parsing prediction: " + predictionStr);
            }
        } else {
            return ResponseEntity.status(404).body("Device not found");
        }
    }

}
