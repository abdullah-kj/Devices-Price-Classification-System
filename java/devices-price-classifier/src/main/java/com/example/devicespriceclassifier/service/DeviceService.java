package com.example.devicespriceclassifier.service;

import com.example.devicespriceclassifier.model.Device;
import com.example.devicespriceclassifier.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> saveDevices(List<Device> devices) {
        return deviceRepository.saveAll(devices);
    }

}
